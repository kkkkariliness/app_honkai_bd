package ru.permyakova.lab7_2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.permyakova.lab7_2.models.Region;
import ru.permyakova.lab7_2.services.RegionService;
import ru.permyakova.lab7_2.services.DungeonService;
import ru.permyakova.lab7_2.services.CharacterService;

import java.util.List;

@Controller
public class PageController {

    private final RegionService regionService;
    private final DungeonService dungeonService;
    private final CharacterService characterService;

    public PageController(RegionService regionService, DungeonService dungeonService, CharacterService characterService) {
        this.regionService = regionService;
        this.dungeonService = dungeonService;
        this.characterService = characterService;
    }

    // Отображение главной страницы со списком регионов, данжей и персонажей
    @GetMapping
    public String showStartPage(Model model) {
        model.addAttribute("regions", regionService.getAllRegions());
        model.addAttribute("dungeons", dungeonService.getAllDungeons());
        model.addAttribute("characters", characterService.getAllCharacters());
        return "start";
    }

    @GetMapping("/regions")
    public String listRegions(Model model) {
        List<Region> regions = regionService.getAllRegions();
        model.addAttribute("regions", regions);
        return "regions";
    }

    @GetMapping("/characters")
    public String pageCharacters() {
        return "characters";
    }

    @GetMapping("/dungeons")
    public String pageDungeons() {
        return "dungeons";
    }

    @GetMapping("/dungeonruns")
    public String pageDungeonRuns() {
        return "dungeonruns";
    }
}
