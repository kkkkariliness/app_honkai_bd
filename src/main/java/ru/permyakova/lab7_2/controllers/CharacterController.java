package ru.permyakova.lab7_2.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.permyakova.lab7_2.models.Character;
import ru.permyakova.lab7_2.services.CharacterService;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/characters")
@AllArgsConstructor
public class CharacterController {
    private final CharacterService characterService;

    /**
     * Отображает форму для создания нового персонажа.
     * @param model Модель для передачи данных в представление.
     * @return Имя представления для создания персонажа.
     */
    @GetMapping("/new")
    public String createCharacterForm(Model model) {
        model.addAttribute("character", new Character());
        return "characters-new";
    }

    /**
     * Сохраняет нового персонажа, полученного из формы.
     * Обрабатывает данные формы, выполняет валидацию и сохраняет персонажа через CharacterService.
     * @param character Объект персонажа, заполненный данными из формы.
     * @param bindingResult Результаты валидации объекта character.
     * @param isAliveValue Значение чекбокса "isAlive" из формы.
     * @return Перенаправление на главную страницу в случае успеха или страницу ошибки.
     */
    @PostMapping("/save")
    public String saveCharacter(@Valid @ModelAttribute Character character,
                                BindingResult bindingResult,
                                @RequestParam(name = "isAlive", required = false) String isAliveValue) {
        character.setAlive(isAliveValue != null && isAliveValue.equals("true"));

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

    /**
     * Отображает форму для редактирования существующего персонажа.
     * @param id Уникальный идентификатор персонажа для редактирования.
     * @param model Модель для передачи данных в представление.
     * @return Имя представления для редактирования персонажа или страницу ошибки, если персонаж не найден.
     */
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

    /**
     * Обновляет информацию о существующем персонаже.
     * Обрабатывает данные формы, выполняет валидацию и обновляет персонажа через CharacterService.
     * @param id Уникальный идентификатор персонажа, который нужно обновить.
     * @param characterDetails Объект персонажа с обновленными данными из формы.
     * @param bindingResult Результаты валидации объекта characterDetails.
     * @param isAliveValue Значение чекбокса "isAlive" из формы.
     * @return Перенаправление на главную страницу в случае успеха или страницу ошибки.
     */
    @PostMapping("/update/{id}")
    public String updateCharacter(@PathVariable UUID id,
                                  @Valid @ModelAttribute("character") Character characterDetails,
                                  BindingResult bindingResult,
                                  @RequestParam(name = "isAlive", required = false) String isAliveValue) {

        characterDetails.setAlive(isAliveValue != null && isAliveValue.equals("true"));

        if (bindingResult.hasErrors()) {
            log.warn("Validation errors while updating character with ID {}: {}", id, bindingResult.getAllErrors());
            characterDetails.setId(id);
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

    /**
     * Удаляет персонажа по его уникальному идентификатору.
     * @param id Уникальный идентификатор персонажа для удаления.
     * @return Перенаправление на главную страницу в случае успеха или страницу ошибки.
     */
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

    /**
     * Отображает информацию о персонаже по его уникальному идентификатору.
     * @param id Уникальный идентификатор персонажа.
     * @param model Модель для передачи данных в представление.
     * @return Имя представления с информацией о персонаже или страницу ошибки, если персонаж не найден.
     */
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