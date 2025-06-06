package ru.permyakova.lab7_2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.permyakova.lab7_2.models.Dungeon;

import java.util.List;

/**
 * Репозиторий для управления сущностями {@link Dungeon}.
 * Предоставляет методы для выполнения операций CRUD и
 * пользовательские запросы к базе данных для данжей.
 */
public interface DungeonRepository extends JpaRepository<Dungeon, Long> {
    // TODO: Найти список данжей по идентификатору региона
    /**
     * Находит список данжей по идентификатору региона.
     * @param regionId Идентификатор региона, к которому относятся данжи.
     * @return Список данжей, найденных в указанном регионе.
     */
    List<Dungeon> findByRegionId(Integer regionId);
}