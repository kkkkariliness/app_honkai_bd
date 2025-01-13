package ru.permyakova.lab7_2.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.permyakova.lab7_2.models.Region;
import ru.permyakova.lab7_2.services.RegionService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/regions")
@AllArgsConstructor
public class RegionController {
    private final RegionService regionService;

    //@GetMapping


    @PostMapping("/new")
    public String createRegionForm(Region region) {
        regionService.saveRegion(region);
        return "redirect:/regions";
    }

    @GetMapping("/new")
    public String showCreateRegionForm() {
        return "regions-new"; // Убедитесь, что у вас есть шаблон regions-new.ftlh
    }


    @PostMapping("/save")
    public String saveRegion(@ModelAttribute Region region) {
        regionService.saveRegion(region);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editRegionForm(@PathVariable Long id, Model model) {
        Optional<Region> optionalRegion = regionService.getRegionById(id);
        if (optionalRegion.isEmpty()) {
            model.addAttribute("error", "Region not found with id: " + id);
            return "error"; // Убедитесь, что у вас есть шаблон error.ftlh
        }
        model.addAttribute("region", optionalRegion.get());
        return "regions-edit";
    }

    @PostMapping("/update/{id}")
    public String updateRegion(@PathVariable Long id, @RequestParam String name) {
        Region region = regionService.getRegionById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid region Id: " + id));
        region.setName(name);
        regionService.saveRegion(region);
        return "redirect:/regions"; // Перенаправление на список всех регионов
    }

    @PostMapping("/{id}")
    public String deleteRegion(@PathVariable Long id) {
        regionService.deleteRegion(id);
        return "redirect:/regions";
    }

    @GetMapping("/{id}")
    public String regionInfo(@PathVariable Long id, Model model) {
        Optional<Region> optionalRegion = regionService.getRegionById(id);
        if (optionalRegion.isEmpty()) {
            model.addAttribute("error", "Region not found with id: " + id);
            return "error"; // Убедитесь, что у вас есть шаблон error.ftlh
        }
        model.addAttribute("region", optionalRegion.get());
        return "regions-info";
    }

}
