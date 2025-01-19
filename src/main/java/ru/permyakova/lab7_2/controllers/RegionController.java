package ru.permyakova.lab7_2.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.permyakova.lab7_2.models.Region;
import ru.permyakova.lab7_2.services.RegionService;
import java.util.Optional;

@Controller
@RequestMapping("/regions")
@AllArgsConstructor
public class RegionController {
    private final RegionService regionService;

    @PostMapping("/new")
    public String createRegionForm(Region region) {
        regionService.saveRegion(region);
        return "redirect:/";
    }

    @GetMapping("/new")
    public String showCreateRegionForm() {
        return "regions-new";
    }


    @PostMapping("/save")
    public String saveRegion(@ModelAttribute Region region) {
        regionService.saveRegion(region);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editRegionForm(@PathVariable long id, Model model) {
        Optional<Region> optionalRegion = regionService.getRegionById(id);
        if (optionalRegion.isEmpty()) {
            model.addAttribute("error", "Region not found with id: " + id);
            return "error"; // Убедитесь, что у вас есть шаблон error.ftlh
        }
        model.addAttribute("region", optionalRegion.get());
        return "regions-edit";
    }

    @PostMapping("/update/{id}")
    public String updateRegion(@PathVariable long id, @RequestParam String name) {
        Region region = regionService.getRegionById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid region Id: " + id));
        region.setName(name);
        regionService.saveRegion(region);
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String deleteRegion(@PathVariable long id) {
        Optional<Region> optionalRegion = regionService.getRegionById(id);
        if (optionalRegion.isEmpty()) {
            throw new IllegalArgumentException("Region with ID " + id + " not found");
        }
        regionService.deleteRegion(id);
        return "redirect:/";
    }


    @GetMapping("/delete/{id}")
    public String regionInfo(@PathVariable long id, Model model) {
        Optional<Region> optionalRegion = regionService.getRegionById(id);
        if (optionalRegion.isEmpty()) {
            model.addAttribute("error", "Region not found with id: " + id);
            return "error";
        }
        model.addAttribute("region", optionalRegion.get());
        return "regions-info";
    }

}
