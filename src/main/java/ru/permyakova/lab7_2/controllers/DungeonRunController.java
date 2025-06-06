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

    /**
     * Сохраняет новую запись о прохождении подземелья.
     * Получает ID подземелья и персонажа из параметров запроса,
     * создает объект DungeonRun, устанавливает случайное время прохождения и сохраняет его.
     * @param dungeon ID выбранного подземелья.
     * @param character UUID выбранного персонажа.
     * @return Перенаправление на страницу со списком прохождений подземелий.
     * @throws IllegalArgumentException если подземелье или персонаж с указанными ID не найдены.
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

    /**
     * Отображает форму для редактирования существующего прохождения подземелья.
     * Загружает данные о прохождении, а также списки регионов, подземелий и персонажей для выбора.
     * @param id ID прохождения подземелья для редактирования.
     * @param model Модель для передачи данных в представление.
     * @return Имя представления для редактирования прохождения подземелья.
     * @throws IllegalArgumentException если прохождение подземелья с указанным ID не найдено.
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

    /**
     * Обновляет информацию о существующем прохождении подземелья.
     * Получает ID прохождения, а также новые ID подземелья и персонажа из параметров запроса.
     * Обновляет данные о прохождении и сохраняет их.
     * @param id ID прохождения подземелья, которое нужно обновить.
     * @param dungeon ID нового подземелья для прохождения.
     * @param character UUID нового персонажа для прохождения.
     * @return Перенаправление на страницу со списком прохождений подземелий.
     * @throws IllegalArgumentException если прохождение подземелья, подземелье или персонаж с указанными ID не найдены.
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

        // Здесь возможно, нужно использовать dungeonRunService.updateDungeonRun(id, dungeonRun);
        // вместо addDungeonRun, если updateDungeonRun существует и обрабатывает обновление
        dungeonRunService.addDungeonRun(dungeonRun); // Предполагается, что addDungeonRun также может обновить существующий объект по ID

        return "redirect:/dungeon-runs";
    }

    /**
     * Удаляет прохождение подземелья по его уникальному идентификатору.
     * @param id ID прохождения подземелья для удаления.
     * @return Перенаправление на страницу со списком прохождений подземелий.
     */
    @PostMapping("/delete/{id}")
    public String deleteDungeonRun(@PathVariable long id) {
        dungeonRunService.deleteDungeonRun(id);
        return "redirect:/dungeon-runs";
    }
}