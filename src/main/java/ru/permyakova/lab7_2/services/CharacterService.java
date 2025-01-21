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


    @Transactional(readOnly = true)
    public List<Character> getAllCharacters() {
        List<Character> characters = characterRepository.findAll(Sort.by("name"));
        return characters;
    }

    @Transactional(readOnly = true)
    public Optional<Character> getCharacterById(UUID id) {
        return characterRepository.findById(id);
    }


    @Transactional
    public void addCharacter(Character character) {
        characterRepository.save(character);
    }

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

    @Transactional
    public void deleteCharacter(UUID id) {
        characterRepository.deleteById(id);
    }

    /**
     * Оживить всех персонажей, донаты на которых больше чем N (захардкодить N)
     * Если сумма донатов всех живых персонажей больше чем M (захардкодить, N << M )
     * то отменить действие и оживить одного случайного персонажа с донатом больше чем N
     * (выбор случайного персонажа сделать через LIMIT 1)
     */

    private static final BigDecimal N = BigDecimal.valueOf(1000);  // Минимальная сумма донатов
    private static final BigDecimal M = BigDecimal.valueOf(50000); // Порог для отката

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