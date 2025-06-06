package ru.permyakova.lab7_2.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.permyakova.lab7_2.models.Dungeon;
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

    // TODO: Отобразить форму для создания нового данжа
    /**
     * Отображает форму для создания нового данжа.
     * Загружает список всех регионов для выбора пользователем.
     * @param model Модель для передачи данных в представление.
     * @return Страница "dungeon-new".
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

    // TODO: Сохранить новый данж
    /**
     * Сохраняет новый данж, полученный из формы.
     * Выполняет валидацию данных и сохраняет данж через DungeonService.
     * @param dungeon Объект данжа, заполненный данными из формы.
     * @param bindingResult Результаты валидации объекта dungeon.
     * @param model Модель для передачи данных в представление в случае ошибки валидации.
     * @return Перенаправление на главную страницу в случае успеха или страницу ошибки.
     */
    @PostMapping("/save")
    public String saveDungeon(@Valid @ModelAttribute Dungeon dungeon,
                              BindingResult bindingResult,
                              Model model) {
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

    // TODO: Обновить информацию о существующем данже
    /**
     * Обновляет информацию о существующем данже.
     * Выполняет валидацию данных и обновляет данж через DungeonService.
     * @param id Уникальный идентификатор данжа, который нужно обновить.
     * @param dungeon Объект данжа с обновленными данными из формы (например, новое имя).
     * @param regionId ID нового региона, выбранного в выпадающем списке.
     * @param bindingResult Результаты валидации объекта dungeon.
     * @param model Модель для передачи данных в представление в случае ошибки валидации.
     * @return Перенаправление на главную страницу в случае успеха или страницу ошибки.
     */
    @PostMapping("/update/{id}")
    public String updateDungeon(@PathVariable long id,
                                @Valid @ModelAttribute Dungeon dungeon,
                                @RequestParam("regionId") long regionId,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            log.warn("Validation errors while updating dungeon with ID {}: {}",
                    id, bindingResult.getAllErrors());
            model.addAttribute("regions", regionService.getAllRegions());
            return "dungeon-edit";
        }
        try {
            dungeonService.updateDungeon(id, dungeon, regionId);
            return "redirect:/";
        } catch (Exception e) {
            log.error("Error updating dungeon with ID {}: {}", id, e.getMessage(), e);
            return "error";
        }
    }

    // TODO: Отобразить форму для редактирования существующего данжа
    /**
     * Отображает форму для редактирования существующего данжа.
     * Загружает данные данжа по ID и список всех регионов.
     * @param id Уникальный идентификатор данжа для редактирования.
     * @param model Модель для передачи данных в представление.
     * @return Страница "dungeon-edit".
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

    // TODO: Удалить данж по его идентификатору
    /**
     * Удаляет данж по его уникальному идентификатору.
     * @param id Уникальный идентификатор данжа для удаления.
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

    // TODO: Отобразить информацию о данже по его идентификатору
    /**
     * Отображает информацию о данже по его уникальному идентификатору.
     * @param id Уникальный идентификатор данжа.
     * @param model Модель для передачи данных в представление.
     * @return Страница "dungeon-info".
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

    // TODO: Получить список данжей по идентификатору региона
    /**
     * Возвращает список данжей по идентификатору региона.
     * Используется для AJAX-запросов.
     * @param regionId Идентификатор региона.
     * @return Список данжей, принадлежащих указанному региону. В случае ошибки возвращает пустой список.
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