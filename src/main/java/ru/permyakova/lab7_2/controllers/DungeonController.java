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

    @PostMapping("/update/{id}")
    public String updateDungeon(@PathVariable long id,
                                @Valid @ModelAttribute Dungeon dungeon,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            log.warn("Validation errors while updating dungeon with ID {}: {}", id, bindingResult.getAllErrors());
            model.addAttribute("regions", regionService.getAllRegions());
            return "dungeon-edit";
        }
        try {
            dungeonService.updateDungeon(id, dungeon);
            return "redirect:/";
        } catch (Exception e) {
            log.error("Error updating dungeon with ID {}: {}", id, e.getMessage(), e);
            return "error";
        }
    }

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
