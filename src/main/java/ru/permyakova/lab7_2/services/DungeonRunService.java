package ru.permyakova.lab7_2.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.permyakova.lab7_2.models.DungeonRun;
import ru.permyakova.lab7_2.repositories.DungeonRunRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class DungeonRunService {
    private final DungeonRunRepository dungeonRunRepository;

    public LocalTime generateRandomTime() {
        Random random = new Random();
        int hours = random.nextInt(2); // Максимум 1 час
        int minutes = random.nextInt(60);
        int seconds = random.nextInt(60);
        return LocalTime.of(hours, minutes, seconds);
    }

    @Transactional(readOnly = true)
    public List<DungeonRun> getAllDungeonRuns() {
        return dungeonRunRepository.findAll(Sort.by("id"));
    }

    @Transactional(readOnly = true)
    public Optional<DungeonRun> getDungeonRunById(Long id) {
        return dungeonRunRepository.findById(id);
    }

    @Transactional
    public void addDungeonRun(DungeonRun dungeonRun) {
        dungeonRunRepository.save(dungeonRun);
    }

    @Transactional
    public void updateDungeonRun(Long id, DungeonRun dungeonRunDetails) {
        DungeonRun dungeonRun = dungeonRunRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid dungeon run Id: " + id));
        dungeonRun.setCharacter(dungeonRunDetails.getCharacter());
        dungeonRun.setTimeScore(dungeonRunDetails.getTimeScore());
        dungeonRunRepository.save(dungeonRun);
    }

    @Transactional
    public void deleteDungeonRun(Long id) {
        dungeonRunRepository.deleteById(id);
    }
}
