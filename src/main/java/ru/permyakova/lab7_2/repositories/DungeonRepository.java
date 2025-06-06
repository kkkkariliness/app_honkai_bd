package ru.permyakova.lab7_2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.permyakova.lab7_2.models.Dungeon;

import java.util.List;

/**
 * Репозиторий для управления сущностями {@link Dungeon}.
 * Предоставляет методы для выполнения операций CRUD и пользовательские запросы к базе данных для подземелий.
 */
public interface DungeonRepository extends JpaRepository<Dungeon, Long> {
    /**
     * Находит список подземелий по идентификатору региона.
     * @param regionId Идентификатор региона, к которому относятся подземелья.
     * @return Список подземелий, найденных в указанном регионе.
     */
    List<Dungeon> findByRegionId(Integer regionId);
}