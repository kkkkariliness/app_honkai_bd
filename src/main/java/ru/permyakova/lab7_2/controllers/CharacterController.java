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

    @GetMapping("/new")
    public String createCharacterForm(Model model) {
        model.addAttribute("character", new Character());
        return "characters-new";
    }

    @PostMapping("/save")
    public String saveCharacter(@ModelAttribute Character character) {
        characterService.addCharacter(character);
        return "redirect:/characters";
    }

    @GetMapping("/edit/{id}")
    public String editCharacterForm(@PathVariable UUID id, Model model) {
        Character character = characterService.getAllCharacters()
                .stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Несуществующий id: " + id));
        model.addAttribute("character", character);
        return "characters-edit";
    }

    @PostMapping("/update/{id}")
    public String updateCharacter(@PathVariable UUID id, @ModelAttribute Character characterDetails) {
        characterService.updateCharacter(id, characterDetails);
        return "redirect:/characters";
    }


    @PostMapping("/delete/{id}")
    public String deleteCharacter(@PathVariable UUID id) {
        characterService.deleteCharacter(id);
        return "redirect:/characters";
    }

    @GetMapping("/{id}")
    public String characterInfo(@PathVariable UUID id, Model model) {
        Character character = characterService.getAllCharacters()
                .stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Несуществующий id: " + id));

        System.out.println("isAlive: " + character.isAlive()); // Проверка значения
        model.addAttribute("character", character);
        return "characters-info";
    }



}
