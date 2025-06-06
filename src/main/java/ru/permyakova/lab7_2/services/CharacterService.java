package ru.permyakova.lab7_2.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.permyakova.lab7_2.models.Character;
import ru.permyakova.lab7_2.repositories.CharacterRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class CharacterService {
    private final CharacterRepository characterRepository;
    private final ActionService actionService;

    /**
     * Получает список всех персонажей, отсортированных по имени.
     * @return Список всех персонажей.
     */
    @Transactional(readOnly = true)
    public List<Character> getAllCharacters() {
        List<Character> characters = characterRepository.findAll(Sort.by("name"));
        return characters;
    }

    /**
     * Получает персонажа по его уникальному идентификатору.
     * @param id Уникальный идентификатор персонажа.
     * @return Optional, содержащий персонажа, если он найден, иначе пустой Optional.
     */
    @Transactional(readOnly = true)
    public Optional<Character> getCharacterById(UUID id) {
        return characterRepository.findById(id);
    }

    /**
     * Добавляет нового персонажа в базу данных.
     * @param character Объект персонажа для добавления.
     */
    @Transactional
    public void addCharacter(Character character) {
        characterRepository.save(character);
    }

    /**
     * Обновляет информацию о существующем персонаже.
     * @param id Уникальный идентификатор персонажа, который нужно обновить.
     * @param characterDetails Объект персонажа с обновленными данными.
     * @throws IllegalArgumentException если персонаж с указанным Id не найден.
     */
    @Transactional
    public void updateCharacter(UUID id, Character characterDetails) {
        Character character = characterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid character Id: " + id));
        character.setName(characterDetails.getName());
        character.setRarity(characterDetails.getRarity());
        character.setWay(characterDetails.getWay());
        character.setTypeOfBattle(characterDetails.getTypeOfBattle());
        character.setDateOfMeet(characterDetails.getDateOfMeet());
        character.setAlive(characterDetails.isAlive());
        character.setMoneyDonat(characterDetails.getMoneyDonat());
        characterRepository.save(character);
    }

    /**
     * Удаляет персонажа из базы данных по его уникальному идентификатору.
     * @param id Уникальный идентификатор персонажа, который нужно удалить.
     */
    @Transactional
    public void deleteCharacter(UUID id) {
        characterRepository.deleteById(id);
    }

    private static final BigDecimal N = BigDecimal.valueOf(500);  // Минимальная сумма донатов
    private static final BigDecimal M = BigDecimal.valueOf(100000); // Порог для отката

    /**
     * Оживляет персонажей на основе их донатов.
     * Создает чекпоинт, оживляет персонажей, чьи донаты превышают N.
     * Если общая сумма донатов живых персонажей превышает M,
     * происходит откат до чекпоинта и оживляется один случайный персонаж.
     * Если донаты ни на одного персонажа не превышают N, происходит откат.
     * В случае ошибки также происходит откат.
     * @throws Exception в случае возникновения непредвиденной ошибки.
     */
    public void reviveCharactersBasedOnDonations() {
        // Создаем чекпоинт
        actionService.saveCheckpoint();
        try {

            // Оживляем всех персонажей с донатами больше N
            List<Character> charactersToRevive = characterRepository.findAllByMoneyDonatGreaterThan(N);
            charactersToRevive.forEach(character -> character.setAlive(true));
            characterRepository.saveAll(charactersToRevive);
            log.info("У персонажей {} сумма доната больше {}", charactersToRevive.size(), N);

            // Общая сумма донатов
            BigDecimal totalDonations = characterRepository.sumDonationsOfAliveCharacters();
            log.info("Итогова сумма донатов: {}", totalDonations);

            if (totalDonations != null) {
                if (totalDonations.compareTo(M) > 0) { // Если сумма донатов превышает M
                    log.warn("Сумма донатов превышает M. Откат на точку сохранения...");
                    actionService.performRollbackToLastCheckpoint();

                    // Оживляем только одного персонажа
                    rollbackAndReviveRandomCharacter();
                } else if (charactersToRevive.isEmpty()) {
                    log.warn("Донат ни на одного персонажа не превышает N. Откат на точку сохранения...");
                    actionService.performRollbackToLastCheckpoint();
                }
                else {
                    actionService.commitCheckpoint(); // Коммитимся, если всё в порядке
                }
            } else log.warn("Ни один донат на персонажа не превысил N");

        } catch (Exception e) {
            log.error("Ошибка. Откат на точку сохранения", e);
            actionService.performRollbackToLastCheckpoint();
            throw e;
        }
    }

    /**
     * Откатывает текущую транзакцию и оживляет одного случайного персонажа,
     * у которого сумма донатов превышает N.
     * Эта операция выполняется в новой транзакции (Propagation.REQUIRES_NEW).
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void rollbackAndReviveRandomCharacter() {

        Optional<Character> randomCharacterOpt = characterRepository.findRandomCharacterByMoneyDonatGreaterThan(N);
        if (randomCharacterOpt.isPresent()) {
            Character randomCharacter = randomCharacterOpt.get();
            randomCharacter.setAlive(true);
            characterRepository.save(randomCharacter);
            log.info("Revived one random character: {}", randomCharacter);
        } else {
            log.warn("No characters found with donations greater than {}", N);
        }
    }

}