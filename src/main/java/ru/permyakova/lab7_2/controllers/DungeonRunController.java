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

    // TODO: Сохранить новую запись о прохождении данжа
    /**
     * Сохраняет новую запись о прохождении данжа.
     * Получает ID данжа и персонажа из параметров запроса,
     * создает объект DungeonRun, устанавливает случайное время прохождения и сохраняет его.
     * @param dungeon ID выбранного данжа.
     * @param character UUID выбранного персонажа.
     * @return Страница "dungeon-runs".
     * @throws IllegalArgumentException если данжа или персонаж с указанными ID не найдены.
     */
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

    // TODO: Отобразить форму для редактирования существующего прохождения данжа
    /**
     * Отображает форму для редактирования существующего прохождения данжа.
     * Загружает данные о прохождении, а также списки регионов, данжей и персонажей для выбора.
     * @param id ID прохождения данжа для редактирования.
     * @param model Модель для передачи данных в представление.
     * @return Страница "dungeonruns-edit".
     * @throws IllegalArgumentException если прохождение данжа с указанным ID не найдено.
     */
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

    // TODO: Обновить информацию о существующем прохождении данжа
    /**
     * Обновляет информацию о существующем прохождении данжа.
     * Получает ID прохождения, а также новые ID данжа и персонажа из параметров запроса.
     * Обновляет данные о прохождении и сохраняет их.
     * @param id ID прохождения данжа, которое нужно обновить.
     * @param dungeon ID нового данжа для прохождения.
     * @param character UUID нового персонажа для прохождения.
     * @return Страница "dungeon-runs".
     * @throws IllegalArgumentException если прохождение данжа,
     * данж или персонаж с указанными ID не найдены.
     */
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

    // TODO: Удалить прохождение данжа по его идентификатору
    /**
     * Удаляет прохождение данжа по его уникальному идентификатору.
     * @param id ID прохождения данжа для удаления.
     * @return Страница "dungeon-runs".
     */
    @PostMapping("/delete/{id}")
    public String deleteDungeonRun(@PathVariable long id) {
        dungeonRunService.deleteDungeonRun(id);
        return "redirect:/dungeon-runs";
    }
}