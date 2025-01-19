package ru.permyakova.lab7_2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.permyakova.lab7_2.models.Dungeon;

import java.util.List;

public interface DungeonRepository extends JpaRepository<Dungeon, Long> {
    List<Dungeon> findByRegionId(Integer regionId);
}
