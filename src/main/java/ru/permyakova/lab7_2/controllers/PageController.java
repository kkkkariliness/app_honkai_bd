package ru.permyakova.lab7_2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.permyakova.lab7_2.models.Character;
import ru.permyakova.lab7_2.models.Dungeon;
import ru.permyakova.lab7_2.models.DungeonRun;
import ru.permyakova.lab7_2.models.Region;
import ru.permyakova.lab7_2.services.RegionService;
import ru.permyakova.lab7_2.services.DungeonService;
import ru.permyakova.lab7_2.services.CharacterService;
import ru.permyakova.lab7_2.services.DungeonRunService;

import java.util.List;

@Controller
public class PageController {

    private final RegionService regionService;
    private final DungeonService dungeonService;
    private final CharacterService characterService;
    private final DungeonRunService dungeonRunService;

    public PageController(RegionService regionService, DungeonService dungeonService,
                          CharacterService characterService, DungeonRunService dungeonRunService) {
        this.regionService = regionService;
        this.dungeonService = dungeonService;
        this.characterService = characterService;
        this.dungeonRunService = dungeonRunService;
    }

    @GetMapping("/")
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
    public String pageCharacters(Model model) {
        List<Character> characters = characterService.getAllCharacters();
        model.addAttribute("characters", characters);
        return "characters";
    }

    @GetMapping("/dungeons")
    public String pageDungeons(Model model) {
        List<Dungeon> dungeons = dungeonService.getAllDungeons();
        model.addAttribute("dungeons", dungeons);
        return "dungeons";
    }

    @GetMapping("/dungeon-runs")
    public String pageDungeonRuns(Model model) {
        List<DungeonRun> dungeonRuns = dungeonRunService.getAllDungeonRuns();
        model.addAttribute("dungeonRuns", dungeonRuns);
        return "dungeon-runs";
    }

}
