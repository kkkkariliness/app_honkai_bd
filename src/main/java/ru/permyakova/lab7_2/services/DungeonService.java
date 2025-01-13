package ru.permyakova.lab7_2.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.permyakova.lab7_2.models.Dungeon;
import ru.permyakova.lab7_2.repositories.DungeonRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DungeonService {
    private final DungeonRepository dungeonRepository;

    @Transactional(readOnly = true)
    public List<Dungeon> getAllDungeons() {
        List<Dungeon> dungeons = dungeonRepository.findAll(Sort.by("name"));
        return dungeons;
    }

    @Transactional(readOnly = true)
    public Optional<Dungeon> getDungeonById(Long id) {
        return dungeonRepository.findById(id);
    }

    @Transactional
    public void addDungeon(Dungeon dungeon) {
        dungeonRepository.save(dungeon);
    }

    @Transactional
    public void updateDungeon(Long id, Dungeon dungeonDetails) {
        Dungeon dungeon = dungeonRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid dungeon Id: " + id));
        dungeon.setName(dungeonDetails.getName());
        dungeon.setRegion(dungeonDetails.getRegion());
        dungeonRepository.save(dungeon);
    }


    @Transactional
    public void deleteDungeon(Long id) {
        dungeonRepository.deleteById(id);
    }
}
