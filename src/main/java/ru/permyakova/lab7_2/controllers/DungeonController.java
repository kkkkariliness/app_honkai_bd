package ru.permyakova.lab7_2.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.permyakova.lab7_2.models.Dungeon;
import ru.permyakova.lab7_2.models.Region;
import ru.permyakova.lab7_2.services.DungeonService;
import ru.permyakova.lab7_2.services.RegionService;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/dungeons")
@AllArgsConstructor
public class DungeonController {
    private final DungeonService dungeonService;
    private final RegionService regionService;

    /**
     * Отображает форму для создания нового подземелья.
     * Загружает список всех регионов для выбора пользователем.
     * @param model Модель для передачи данных в представление.
     * @return Имя представления для создания подземелья.
     */
    @GetMapping("/new")
    public String createDungeonForm(Model model) {
        try {
            model.addAttribute("dungeon", new Dungeon());
            model.addAttribute("regions", regionService.getAllRegions());
            return "dungeon-new";
        } catch (Exception e) {
            log.error("Error creating dungeon form: {}", e.getMessage(), e);
            return "error";
        }
    }

    /**
     * Сохраняет новое подземелье, полученное из формы.
     * Выполняет валидацию данных и сохраняет подземелье через DungeonService.
     * @param dungeon Объект подземелья, заполненный данными из формы.
     * @param bindingResult Результаты валидации объекта dungeon.
     * @param model Модель для передачи данных в представление в случае ошибки валидации.
     * @return Перенаправление на главную страницу в случае успеха или страницу ошибки.
     */
    @PostMapping("/save")
    public String saveDungeon(@Valid @ModelAttribute Dungeon dungeon, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            log.warn("Validation errors while saving dungeon: {}", bindingResult.getAllErrors());
            model.addAttribute("regions", regionService.getAllRegions());
            return "dungeon-new";
        }
        try {
            dungeonService.addDungeon(dungeon);
            return "redirect:/";
        } catch (Exception e) {
            log.error("Error saving dungeon: {}", e.getMessage(), e);
            return "error";
        }
    }

    /**
     * Обновляет информацию о существующем подземелье.
     * Выполняет валидацию данных и обновляет подземелье через DungeonService.
     * @param id Уникальный идентификатор подземелья, которое нужно обновить.
     * @param dungeon Объект подземелья с обновленными данными из формы (например, новое имя).
     * @param regionId ID нового региона, выбранного в выпадающем списке.
     * @param bindingResult Результаты валидации объекта dungeon.
     * @param model Модель для передачи данных в представление в случае ошибки валидации.
     * @return Перенаправление на главную страницу в случае успеха или страницу ошибки.
     */
    @PostMapping("/update/{id}")
    public String updateDungeon(@PathVariable long id,
                                @Valid @ModelAttribute Dungeon dungeon,
                                @RequestParam("regionId") long regionId, // Получаем ID региона из формы
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            log.warn("Validation errors while updating dungeon with ID {}: {}", id, bindingResult.getAllErrors());
            // Если есть ошибки, нужно снова загрузить регионы для отображения формы
            model.addAttribute("regions", regionService.getAllRegions());
            return "dungeon-edit";
        }
        try {
            // Вызываем обновленный метод сервиса
            dungeonService.updateDungeon(id, dungeon, regionId);
            return "redirect:/";
        } catch (Exception e) {
            log.error("Error updating dungeon with ID {}: {}", id, e.getMessage(), e);
            return "error";
        }
    }

    /**
     * Отображает форму для редактирования существующего подземелья.
     * Загружает данные подземелья по ID и список всех регионов.
     * @param id Уникальный идентификатор подземелья для редактирования.
     * @param model Модель для передачи данных в представление.
     * @return Имя представления для редактирования подземелья или страницу ошибки, если подземелье не найдено.
     */
    @GetMapping("/edit/{id}")
    public String editDungeonForm(@PathVariable long id, Model model) {
        try {
            Dungeon dungeon = dungeonService.getDungeonById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid dungeon Id: " + id));
            model.addAttribute("dungeon", dungeon);
            model.addAttribute("regions", regionService.getAllRegions());
            return "dungeon-edit";
        } catch (Exception e) {
            log.error("Error fetching dungeon for editing: {}", e.getMessage(), e);
            return "error";
        }
    }

    /**
     * Удаляет подземелье по его уникальному идентификатору.
     * @param id Уникальный идентификатор подземелья для удаления.
     * @return Перенаправление на главную страницу в случае успеха или страницу ошибки.
     */
    @PostMapping("/delete/{id}")
    public String deleteDungeon(@PathVariable long id) {
        try {
            Optional<Dungeon> optionalDungeon = dungeonService.getDungeonById(id);
            if (optionalDungeon.isEmpty()) {
                throw new IllegalArgumentException("Dungeon with ID " + id + " not found");
            }
            dungeonService.deleteDungeon(id);
            return "redirect:/";
        } catch (Exception e) {
            log.error("Error deleting dungeon with ID {}: {}", id, e.getMessage(), e);
            return "error";
        }
    }

    /**
     * Отображает информацию о подземелье по его уникальному идентификатору.
     * @param id Уникальный идентификатор подземелья.
     * @param model Модель для передачи данных в представление.
     * @return Имя представления с информацией о подземелье или страницу ошибки, если возникла проблема.
     */
    @GetMapping("/delete/{id}")
    public String dungeonInfo(@PathVariable long id, Model model) {
        try {
            Dungeon dungeon = dungeonService.getDungeonById(id).orElse(null);
            model.addAttribute("dungeon", dungeon);
            return "dungeon-info";
        } catch (Exception e) {
            log.error("Error fetching dungeon info: {}", e.getMessage(), e);
            return "error";
        }
    }

    /**
     * Возвращает список подземелий по идентификатору региона.
     * Используется для AJAX-запросов.
     * @param regionId Идентификатор региона.
     * @return Список подземелий, принадлежащих указанному региону. В случае ошибки возвращает пустой список.
     */
    @GetMapping("/by-region/{regionId}")
    @ResponseBody
    public List<Dungeon> getDungeonsByRegion(@PathVariable Integer regionId) {
        try {
            return dungeonService.getDungeonsByRegion(regionId);
        } catch (Exception e) {
            log.error("Error fetching dungeons by region ID {}: {}", regionId, e.getMessage(), e);
            return List.of();
        }
    }
}