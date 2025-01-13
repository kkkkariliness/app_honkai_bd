package ru.permyakova.lab7_2.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.permyakova.lab7_2.models.DungeonRun;
import ru.permyakova.lab7_2.repositories.DungeonRunRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class DungeonRunService {
    private final DungeonRunRepository dungeonRunRepository;

    @Transactional(readOnly = true)
    public List<DungeonRun> getAllDungeonRuns() {
        return dungeonRunRepository.findAll(Sort.by("name"));
    }

    @Transactional
    public void addDungeonRun(DungeonRun dungeonRun) {
        dungeonRunRepository.save(dungeonRun);
    }

    @Transactional
    public void updateDungeonRun(Long id, DungeonRun dungeonRunDetails) {
        DungeonRun dungeonRun = dungeonRunRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid dungeon run Id: " + id));
        dungeonRun.setId(dungeonRunDetails.getId());
        dungeonRun.setCharacter(dungeonRunDetails.getCharacter());
        dungeonRun.setTimeScore(dungeonRunDetails.getTimeScore());
        dungeonRunRepository.save(dungeonRun);
    }

    @Transactional
    public void deleteDungeonRun(Long id) {
        dungeonRunRepository.deleteById(id);
    }
}
