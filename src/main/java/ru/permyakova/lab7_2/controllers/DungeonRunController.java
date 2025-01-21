package ru.permyakova.lab7_2.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.permyakova.lab7_2.models.Character;
import ru.permyakova.lab7_2.models.Dungeon;
import ru.permyakova.lab7_2.models.DungeonRun;
import ru.permyakova.lab7_2.services.CharacterService;
import ru.permyakova.lab7_2.services.DungeonRunService;
import ru.permyakova.lab7_2.services.DungeonService;
import ru.permyakova.lab7_2.services.RegionService;

import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/dungeon-runs")
@AllArgsConstructor
public class DungeonRunController {
    private final DungeonRunService dungeonRunService;
    private final DungeonService dungeonService;
    private final CharacterService characterService;
    private final RegionService regionService;

    @PostMapping
    public String saveDungeonRun(@RequestParam Long dungeon, @RequestParam UUID character) {

        DungeonRun dungeonRun = new DungeonRun();

        Dungeon selectedDungeon = dungeonService.getDungeonById(dungeon)
                .orElseThrow(() -> new IllegalArgumentException("Нет данжа с ID: " + dungeon));
        Character selectedCharacter = characterService.getCharacterById(character)
                .orElseThrow(() -> new IllegalArgumentException("Нет персонажа с ID: " + character));

        dungeonRun.setDungeon(selectedDungeon);
        dungeonRun.setCharacter(selectedCharacter);

        dungeonRun.setTimeScore(dungeonRunService.generateRandomTime());

        dungeonRunService.addDungeonRun(dungeonRun);

        return "redirect:/dungeon-runs";
    }

    @GetMapping("/edit/{id}")
    public String editDungeonRunForm(@PathVariable long id, Model model) {
        DungeonRun dungeonRun = dungeonRunService.getDungeonRunById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid dungeon run Id: " + id));

        model.addAttribute("dungeonRun", dungeonRun);
        model.addAttribute("regions", regionService.getAllRegions());
        model.addAttribute("dungeons", dungeonService.getAllDungeons());
        model.addAttribute("characters", characterService.getAllCharacters());
        return "dungeonruns-edit";
    }

    @PostMapping("/update")
    public String updateDungeonRun(
            @RequestParam long id,
            @RequestParam long dungeon,
            @RequestParam UUID character) {

        DungeonRun dungeonRun = dungeonRunService.getDungeonRunById(id)
                .orElseThrow(() -> new IllegalArgumentException("Нет похода в данж с Id: " + id));
        Dungeon selectedDungeon = dungeonService.getDungeonById(dungeon)
                .orElseThrow(() -> new IllegalArgumentException("Нет данжа с ID: " + dungeon));
        Character selectedCharacter = characterService.getCharacterById(character)
                .orElseThrow(() -> new IllegalArgumentException("Нет персонажа ID: " + character));

        dungeonRun.setDungeon(selectedDungeon);
        dungeonRun.setCharacter(selectedCharacter);

        dungeonRun.setTimeScore(dungeonRunService.generateRandomTime());

        dungeonRunService.addDungeonRun(dungeonRun);

        return "redirect:/dungeon-runs";
    }

    @PostMapping("/delete/{id}")
    public String deleteDungeonRun(@PathVariable long id) {
        dungeonRunService.deleteDungeonRun(id);
        return "redirect:/dungeon-runs";
    }
}
