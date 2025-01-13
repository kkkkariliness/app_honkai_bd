package ru.permyakova.lab7_2.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.permyakova.lab7_2.models.Character;
import ru.permyakova.lab7_2.services.CharacterService;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/characters")
@AllArgsConstructor
public class CharacterController {
    private final CharacterService characterService;

    @GetMapping("/")
    public String listCharacters(Model model) {
        List<Character> characters = characterService.getAllCharacters();
        model.addAttribute("characters", characters);
        return "character-list";
    }

    @GetMapping("/new")
    public String createCharacterForm(Model model) {
        model.addAttribute("character", new Character());
        return "character-form";
    }

    @PostMapping("/save")
    public String saveCharacter(@ModelAttribute Character character) {
        characterService.addCharacter(character);
        return "redirect:/characters/";
    }

    @GetMapping("/edit/{id}")
    public String editCharacterForm(@PathVariable UUID id, Model model) {
        Character character = characterService.getAllCharacters()
                .stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
        model.addAttribute("character", character);
        return "character-form";
    }

    @PostMapping("/delete/{id}")
    public String deleteCharacter(@PathVariable UUID id) {
        characterService.deleteCharacter(id);
        return "redirect:/characters/";
    }
}
