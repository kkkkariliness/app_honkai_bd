package ru.permyakova.lab7_2.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.permyakova.lab7_2.models.Region;
import ru.permyakova.lab7_2.services.RegionService;

import java.util.Optional;

@Controller
@RequestMapping("/regions")
@AllArgsConstructor
public class RegionController {

    private final RegionService regionService;

    /**
     * Отображает форму для создания нового региона.
     * @param model Модель для передачи данных в представление.
     * @return Имя представления для создания региона.
     */
    @GetMapping("/new")
    public String showCreateRegionForm(Model model) {
        model.addAttribute("region", new Region());
        return "regions-new";
    }

    /**
     * Создает новый регион.
     * Обрабатывает данные формы, выполняет валидацию и сохраняет регион через RegionService.
     * @param region Объект региона, заполненный данными из формы.
     * @param bindingResult Результаты валидации объекта region.
     * @param model Модель для передачи данных в представление в случае ошибки валидации.
     * @return Перенаправление на главную страницу в случае успеха или страницу ошибки.
     */
    @PostMapping("/new")
    public String createRegion(@Valid @ModelAttribute Region region, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "regions-new";
        }
        try {
            regionService.saveRegion(region);
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при создании региона: " + e.getMessage());
            return "error";
        }
        return "redirect:/";
    }

    /**
     * Сохраняет (добавляет или обновляет) регион.
     * Этот метод может быть использован как для создания, так и для обновления региона,
     * если поле ID в объекте `region` будет установлено.
     * @param region Объект региона для сохранения.
     * @param bindingResult Результаты валидации объекта region.
     * @param model Модель для передачи данных в представление в случае ошибки валидации.
     * @return Перенаправление на главную страницу в случае успеха или страницу ошибки.
     */
    @PostMapping("/save")
    public String saveRegion(@Valid @ModelAttribute Region region, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "regions-new";
        }
        try {
            regionService.saveRegion(region);
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при сохранении региона: " + e.getMessage());
            return "error";
        }
        return "redirect:/";
    }

    /**
     * Отображает форму для редактирования существующего региона.
     * @param id Уникальный идентификатор региона для редактирования.
     * @param model Модель для передачи данных в представление.
     * @return Имя представления для редактирования региона или страницу ошибки, если регион не найден.
     */
    @GetMapping("/edit/{id}")
    public String editRegionForm(@PathVariable long id, Model model) {
        Optional<Region> optionalRegion = regionService.getRegionById(id);
        if (optionalRegion.isEmpty()) {
            model.addAttribute("error", "Регион с ID " + id + " не найден.");
            return "error";
        }
        model.addAttribute("region", optionalRegion.get());
        return "regions-edit";
    }

    /**
     * Обновляет информацию о существующем регионе.
     * Обрабатывает данные формы, выполняет валидацию и обновляет регион через RegionService.
     * @param id Уникальный идентификатор региона, который нужно обновить.
     * @param region Объект региона с обновленными данными из формы.
     * @param bindingResult Результаты валидации объекта region.
     * @param model Модель для передачи данных в представление в случае ошибки валидации.
     * @return Перенаправление на главную страницу в случае успеха или страницу ошибки.
     */
    @PostMapping("/update/{id}")
    public String updateRegion(@PathVariable long id, @Valid @ModelAttribute Region region, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("region", region);
            return "regions-edit";
        }
        try {
            Region existingRegion = regionService.getRegionById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Регион с ID " + id + " не найден"));
            existingRegion.setName(region.getName());
            regionService.saveRegion(existingRegion);
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при обновлении региона: " + e.getMessage());
            return "error";
        }
        return "redirect:/";
    }

    /**
     * Удаляет регион по его уникальному идентификатору.
     * @param id Уникальный идентификатор региона для удаления.
     * @param model Модель для передачи данных в представление в случае ошибки.
     * @return Перенаправление на главную страницу в случае успеха или страницу ошибки.
     */
    @PostMapping("/delete/{id}")
    public String deleteRegion(@PathVariable long id, Model model) {
        try {
            regionService.getRegionById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Регион с ID " + id + " не найден"));
            regionService.deleteRegion(id);
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при удалении региона: " + e.getMessage());
            return "error";
        }
        return "redirect:/";
    }

    /**
     * Отображает информацию о регионе по его уникальному идентификатору.
     * @param id Уникальный идентификатор региона.
     * @param model Модель для передачи данных в представление.
     * @return Имя представления с информацией о регионе или страницу ошибки, если регион не найден.
     */
    @GetMapping("/delete/{id}")
    public String regionInfo(@PathVariable long id, Model model) {
        Optional<Region> optionalRegion = regionService.getRegionById(id);
        if (optionalRegion.isEmpty()) {
            model.addAttribute("error", "Регион с ID " + id + " не найден.");
            return "error";
        }
        model.addAttribute("region", optionalRegion.get());
        return "regions-info";
    }
}