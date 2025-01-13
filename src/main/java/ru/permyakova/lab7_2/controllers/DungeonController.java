package ru.permyakova.lab7_2.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.permyakova.lab7_2.models.Dungeon;
import ru.permyakova.lab7_2.services.DungeonService;

import java.util.List;

@Controller
@RequestMapping("/dungeons")
@AllArgsConstructor
public class DungeonController {
    private final DungeonService dungeonService;

    // Метод для отображения всех данжей
    @GetMapping("/")
    public String listDungeons(Model model) {
        List<Dungeon> dungeons = dungeonService.getAllDungeons();
        model.addAttribute("dungeons", dungeons);
        return "/"; // Ссылается на HTML-шаблон dungeon-list.ftlh
    }

    @GetMapping("/new")
    public String createDungeonForm(Model model) {
        model.addAttribute("dungeon", new Dungeon());
        return "dungeon-form";
    }

    @PostMapping("/save")
    public String saveDungeon(@ModelAttribute Dungeon dungeon) {
        dungeonService.addDungeon(dungeon);
        return "redirect:/dungeons/";
    }

    @GetMapping("/edit/{id}")
    public String editDungeonForm(@PathVariable Long id, Model model) {
        Dungeon dungeon = dungeonService.getAllDungeons()
                .stream()
                .filter(d -> d.getId().equals(id))
                .findFirst()
                .orElse(null);
        model.addAttribute("dungeon", dungeon);
        return "dungeon-form";
    }

    @PostMapping("/delete/{id}")
    public String deleteDungeon(@PathVariable Long id) {
        dungeonService.deleteDungeon(id);
        return "redirect:/dungeons/";
    }
}
