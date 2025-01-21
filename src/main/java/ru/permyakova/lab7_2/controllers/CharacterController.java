package ru.permyakova.lab7_2.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.permyakova.lab7_2.models.Character;
import ru.permyakova.lab7_2.services.CharacterService;

import jakarta.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@Slf4j
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
    public String saveCharacter(@Valid @ModelAttribute Character character, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            log.warn("Validation errors while saving character: {}", bindingResult.getAllErrors());
            return "characters-new";
        }
        try {
            characterService.addCharacter(character);
            return "redirect:/";
        } catch (Exception e) {
            log.error("Error saving character: {}", e.getMessage(), e);
            return "error";
        }
    }

    @GetMapping("/edit/{id}")
    public String editCharacterForm(@PathVariable UUID id, Model model) {
        try {
            log.debug("Request to edit character with ID: {}", id);
            Optional<Character> character = characterService.getCharacterById(id);
            if (character.isPresent()) {
                model.addAttribute("character", character.get());
                return "characters-edit";
            } else {
                log.warn("Character with ID {} not found", id);
                return "error";
            }
        } catch (Exception e) {
            log.error("Error fetching character for editing: {}", e.getMessage(), e);
            return "error";
        }
    }

    @PostMapping("/update/{id}")
    public String updateCharacter(@PathVariable UUID id,
                                  @Valid @ModelAttribute Character characterDetails,
                                  BindingResult bindingResult,
                                  Model model) {
        if (bindingResult.hasErrors()) {
            log.warn("Validation errors while updating character with ID {}: {}", id, bindingResult.getAllErrors());
            model.addAttribute("character", characterDetails);
            return "characters-edit";
        }
        try {
            characterService.updateCharacter(id, characterDetails);
            return "redirect:/";
        } catch (Exception e) {
            log.error("Error updating character with ID {}: {}", id, e.getMessage(), e);
            return "error";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteCharacter(@PathVariable UUID id) {
        try {
            characterService.deleteCharacter(id);
            return "redirect:/";
        } catch (Exception e) {
            log.error("Error deleting character with ID {}: {}", id, e.getMessage(), e);
            return "error";
        }
    }

    @GetMapping("/delete/{id}")
    public String characterInfo(@PathVariable UUID id, Model model) {
        try {
            Optional<Character> character = characterService.getCharacterById(id);
            if (character.isPresent()) {
                model.addAttribute("character", character.get());
                return "characters-info";
            } else {
                log.warn("Character with ID {} not found", id);
                return "error";
            }
        } catch (Exception e) {
            log.error("Error fetching character info: {}", e.getMessage(), e);
            return "error";
        }
    }
}
