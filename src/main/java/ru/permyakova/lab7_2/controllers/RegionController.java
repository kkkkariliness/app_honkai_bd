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

    @GetMapping("/new")
    public String showCreateRegionForm(Model model) {
        model.addAttribute("region", new Region());
        return "regions-new";
    }

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
