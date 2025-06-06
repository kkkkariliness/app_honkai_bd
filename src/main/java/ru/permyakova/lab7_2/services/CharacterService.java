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

    // TODO: Получить список всех персонажей, отсортированных по имени
    /**
     * Получает список всех персонажей, отсортированных по имени.
     * @return Список всех персонажей.
     */
    @Transactional(readOnly = true)
    public List<Character> getAllCharacters() {
        List<Character> characters = characterRepository.findAll(Sort.by("name"));
        return characters;
    }

    // TODO: Получить персонажа по его уникальному идентификатору
    /**
     * Получает персонажа по его уникальному идентификатору.
     * @param id Уникальный идентификатор персонажа.
     * @return Optional, содержащий персонажа, если он найден, иначе пустой Optional.
     */
    @Transactional(readOnly = true)
    public Optional<Character> getCharacterById(UUID id) {
        return characterRepository.findById(id);
    }

    // TODO: Добавить нового персонажа в базу данных
    /**
     * Добавляет нового персонажа в базу данных.
     * @param character Объект персонажа для добавления.
     */
    @Transactional
    public void addCharacter(Character character) {
        characterRepository.save(character);
    }

    // TODO: Обновить информацию о существующем персонаже
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

    // TODO: Удалить персонажа из базы данных по его уникальному идентификатору
    /**
     * Удаляет персонажа из базы данных по его уникальному идентификатору.
     * @param id Уникальный идентификатор персонажа, который нужно удалить.
     */
    @Transactional
    public void deleteCharacter(UUID id) {
        characterRepository.deleteById(id);
    }

    private static final BigDecimal N = BigDecimal.valueOf(500);
    private static final BigDecimal M = BigDecimal.valueOf(100000);

    // TODO: Оживить персонажей на основе их донатов
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
        actionService.saveCheckpoint();
        try {
            List<Character> charactersToRevive = characterRepository
                    .findAllByMoneyDonatGreaterThan(N);
            charactersToRevive.forEach(character -> character.setAlive(true));
            characterRepository.saveAll(charactersToRevive);
            log.info("У персонажей {} сумма доната больше {}", charactersToRevive.size(), N);

            BigDecimal totalDonations = characterRepository.sumDonationsOfAliveCharacters();
            log.info("Итогова сумма донатов: {}", totalDonations);

            if (totalDonations != null) {
                if (totalDonations.compareTo(M) > 0) {
                    log.warn("Сумма донатов превышает M. Откат на точку сохранения...");
                    actionService.performRollbackToLastCheckpoint();

                    rollbackAndReviveRandomCharacter();
                } else if (charactersToRevive.isEmpty()) {
                    log.warn("Донат ни на одного персонажа не превышает N. " +
                            "Откат на точку сохранения...");
                    actionService.performRollbackToLastCheckpoint();
                }
                else {
                    actionService.commitCheckpoint();
                }
            } else log.warn("Ни один донат на персонажа не превысил N");

        } catch (Exception e) {
            log.error("Ошибка. Откат на точку сохранения", e);
            actionService.performRollbackToLastCheckpoint();
            throw e;
        }
    }

    // TODO: Откатить текущую транзакцию и оживить одного случайного персонажа
    /**
     * Откатывает текущую транзакцию и оживляет одного случайного персонажа,
     * у которого сумма донатов превышает N.
     * Эта операция выполняется в новой транзакции (Propagation.REQUIRES_NEW).
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void rollbackAndReviveRandomCharacter() {

        Optional<Character> randomCharacterOpt =
                characterRepository.findRandomCharacterByMoneyDonatGreaterThan(N);
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