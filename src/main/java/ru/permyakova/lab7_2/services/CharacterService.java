package ru.permyakova.lab7_2.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.permyakova.lab7_2.models.Character;
import ru.permyakova.lab7_2.repositories.CharacterRepository;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CharacterService {
    private final CharacterRepository characterRepository;

    @Transactional(readOnly = true)
    public List<Character> getAllCharacters() {
        return characterRepository.findAll(Sort.by("name"));
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
}
