package ru.permyakova.lab7_2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.permyakova.lab7_2.models.Dungeon;

public interface DungeonRepository extends JpaRepository<Dungeon, Long> {
}
