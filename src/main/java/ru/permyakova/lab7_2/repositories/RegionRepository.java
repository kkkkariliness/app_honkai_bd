package ru.permyakova.lab7_2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.permyakova.lab7_2.models.Region;

import java.util.List;

public interface RegionRepository extends JpaRepository<Region, Long> {
    List<Region> findByName(String name);
    @Query("SELECT r FROM Region r")
    List<Region> getAllRegions();
}
