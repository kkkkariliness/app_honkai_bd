package ru.permyakova.lab7_2.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.permyakova.lab7_2.models.Character;
import ru.permyakova.lab7_2.services.CharacterService;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/characters")
@AllArgsConstructor
public class CharacterController {
    private final CharacterService characterService;

    @GetMapping("/new")
    public String createCharacterForm(Model model) {
        model.addAttribute("character", new Character());
        return "characters-new";
    }

    @PostMapping("/save")
    public String saveCharacter(@ModelAttribute Character character) {
        characterService.addCharacter(character);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editCharacterForm(@PathVariable UUID id, Model model) {
        Optional<Character> character = characterService.getCharacterById(id);
        model.addAttribute("character", character);
        return "characters-edit";
    }

    @PostMapping("/update/{id}")
    public String updateCharacter(@PathVariable UUID id, @ModelAttribute Character characterDetails) {
        characterService.updateCharacter(id, characterDetails);
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String deleteCharacter(@PathVariable UUID id) {
        characterService.deleteCharacter(id);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String characterInfo(@PathVariable UUID id, Model model) {
        Optional<Character> character = characterService.getCharacterById(id);
        if (character.isPresent()) {
            model.addAttribute("character", character.get());
        } else {
            return "error-page"; // Замените на ваш шаблон для страницы ошибки
        }
        return "characters-info";
    }

}
