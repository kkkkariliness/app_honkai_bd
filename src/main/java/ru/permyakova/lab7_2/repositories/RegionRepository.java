package ru.permyakova.lab7_2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.permyakova.lab7_2.models.Region;

import java.util.List;

/**
 * Репозиторий для управления сущностями {@link Region}.
 * Предоставляет методы для выполнения операций CRUD и
 * пользовательские запросы к базе данных для регионов.
 */
public interface RegionRepository extends JpaRepository<Region, Long> {
}