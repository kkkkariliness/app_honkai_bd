package ru.permyakova.lab7_2.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.permyakova.lab7_2.models.DungeonRun;
import ru.permyakova.lab7_2.services.DungeonRunService;

import java.util.List;

@Controller
@RequestMapping("/dungeon-runs")
@AllArgsConstructor
public class DungeonRunController {
    private final DungeonRunService dungeonRunService;

//    @GetMapping("/start")
//    public String showDungeonRunForm(Model model) {
//        // Загрузка данных для выпадающих списков
//        model.addAttribute("regions", regionService.getAllRegions());
//        model.addAttribute("dungeons", dungeonService.getAllDungeons());
//        model.addAttribute("characters", characterService.getAllCharacters());
//        return "dungeon-run-start"; // Ваш HTML-шаблон
//    }

    @GetMapping("/")
    public String listDungeonRuns(Model model) {
        List<DungeonRun> dungeonRuns = dungeonRunService.getAllDungeonRuns();
        model.addAttribute("dungeonRuns", dungeonRuns);
        return "redirect:/";
    }

    @GetMapping("/new")
    public String createDungeonRunForm(Model model) {
        model.addAttribute("dungeonRun", new DungeonRun());
        return "dungeon-run-form";
    }

    @PostMapping("/save")
    public String saveDungeonRun(@ModelAttribute DungeonRun dungeonRun) {
        dungeonRunService.addDungeonRun(dungeonRun);
        return "redirect:/dungeon-runs/";
    }

    @GetMapping("/edit/{id}")
    public String editDungeonRunForm(@PathVariable Long id, Model model) {
        DungeonRun dungeonRun = dungeonRunService.getAllDungeonRuns()
                .stream()
                .filter(dr -> dr.getId().equals(id))
                .findFirst()
                .orElse(null);
        model.addAttribute("dungeonRun", dungeonRun);
        return "dungeon-run-form";
    }

    @PostMapping("/delete/{id}")
    public String deleteDungeonRun(@PathVariable Long id) {
        dungeonRunService.deleteDungeonRun(id);
        return "redirect:/dungeon-runs/";
    }
}
