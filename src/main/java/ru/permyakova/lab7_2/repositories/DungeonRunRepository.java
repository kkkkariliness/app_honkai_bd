package ru.permyakova.lab7_2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.permyakova.lab7_2.models.DungeonRun;

/**
 * Репозиторий для управления сущностями {@link DungeonRun}.
 * Предоставляет стандартные методы для выполнения операций CRUD (Create, Read, Update, Delete)
 * с записями о прохождениях данжей.
 */
public interface DungeonRunRepository extends JpaRepository<DungeonRun, Long> {
}