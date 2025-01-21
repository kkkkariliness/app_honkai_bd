package ru.permyakova.lab7_2.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.permyakova.lab7_2.models.DungeonRun;
import ru.permyakova.lab7_2.services.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Controller
public class PageController {

    private final RegionService regionService;
    private final DungeonService dungeonService;
    private final CharacterService characterService;
    private final DungeonRunService dungeonRunService;
    private final ActionService actionService;

    @GetMapping("/")
    public String showStartPage(Model model) {
        model.addAttribute("regions", regionService.getAllRegions());
        model.addAttribute("dungeons", dungeonService.getAllDungeons());
        model.addAttribute("characters", characterService.getAllCharacters());
        return "start";
    }

    @GetMapping("/dungeon-runs")
    public String pageDungeonRuns(Model model) {
        List<DungeonRun> dungeonRuns = dungeonRunService.getAllDungeonRuns();
        model.addAttribute("dungeonRuns", dungeonRuns);
        return "dungeon-runs";
    }

    @PostMapping("/reviveCharacters")
    public ResponseEntity<String> reviveCharacters() {
        try {
            characterService.reviveCharactersBasedOnDonations();
            return ResponseEntity.ok("Персонажи успешно оживлены");
        } catch (Exception ex) {
            log.error("Ошибка при оживлении персонажей", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

}
