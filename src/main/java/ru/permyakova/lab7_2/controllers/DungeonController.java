package ru.permyakova.lab7_2.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.permyakova.lab7_2.models.Dungeon;
import ru.permyakova.lab7_2.models.Region;
import ru.permyakova.lab7_2.services.DungeonService;
import ru.permyakova.lab7_2.services.RegionService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/dungeons")
@AllArgsConstructor
public class DungeonController {
    private final DungeonService dungeonService;
    private final RegionService regionService;

    @GetMapping("/new")
    public String createDungeonForm(Model model) {
        model.addAttribute("dungeon", new Dungeon());
        model.addAttribute("regions", regionService.getAllRegions());
        return "dungeon-new";
    }

    @PostMapping("/save")
    public String saveDungeon(@ModelAttribute Dungeon dungeon) {
        dungeonService.addDungeon(dungeon);
        return "redirect:/";
    }

    @PostMapping("/update/{id}")
    public String updateDungeon(@PathVariable long id,
                                @RequestParam String name,
                                @RequestParam Integer regionId) {
        Dungeon dungeon = dungeonService.getDungeonById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid dungeon Id: " + id));

        dungeon.setName(name);
        dungeon.setRegion(regionService.getRegionById(regionId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid region Id: " + regionId)));

        dungeonService.updateDungeon(id, dungeon);
        return "redirect:/";
    }


    @GetMapping("/edit/{id}")
    public String editDungeonForm(@PathVariable long id, Model model) {
        Dungeon dungeon = dungeonService.getDungeonById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid dungeon Id: " + id));
        model.addAttribute("dungeon", dungeon);
        model.addAttribute("regions", regionService.getAllRegions());
        return "dungeon-edit";
    }


    @PostMapping("/delete/{id}")
    public String deleteDungeon(@PathVariable long id) {
        Optional<Dungeon> optionalDungeon = dungeonService.getDungeonById(id);
        if (optionalDungeon.isEmpty()) {
            throw new IllegalArgumentException("Region with ID " + id + " not found");
        }
        dungeonService.deleteDungeon(id);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String dungeonInfo(@PathVariable long id, Model model) {
        Dungeon dungeon = dungeonService.getDungeonById(id).orElse(null);
        model.addAttribute("dungeon", dungeon);
        return "dungeon-info";
    }

    @GetMapping("/by-region/{regionId}")
    @ResponseBody
    public List<Dungeon> getDungeonsByRegion(@PathVariable Integer regionId) {
        return dungeonService.getDungeonsByRegion(regionId);
    }


}
