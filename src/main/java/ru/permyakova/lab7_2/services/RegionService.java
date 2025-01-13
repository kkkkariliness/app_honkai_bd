package ru.permyakova.lab7_2.services;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.permyakova.lab7_2.models.Dungeon;
import ru.permyakova.lab7_2.models.Region;
import ru.permyakova.lab7_2.repositories.RegionRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegionService {
    private final RegionRepository regionRepository;

    @Transactional(readOnly = true)
    public List<Region> getAllRegions() {
        List<Region> regions = regionRepository.findAll(Sort.by("name"));
        log.info("Regions fetched: " + regions);
        return regions;
    }

    @Transactional
    public void addRegion(Region region) {
        regionRepository.save(region);
    }

    @Transactional
    public void updateRegion(Long id, Region regionDetails) {
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid region Id: " + id));
        region.setName(regionDetails.getName());
        regionRepository.save(region);
    }

    @Transactional
    public List<Region> getRegion(String name) {
        if (name != null) {
            return regionRepository.findByName(name);
        }
        return regionRepository.findAll();
    }

    @Transactional
    public void saveRegion(Region region) {
        log.info("Saving/updating region: " + region);
        regionRepository.save(region);
    }

    @Transactional
    public Optional<Region> getRegionById(long id) {
        return regionRepository.findById(id);
    }

    @Transactional
    public void deleteRegion(long id) {
        if (regionRepository.existsById(id)) {
            regionRepository.deleteById(id);
        } else {
            log.warn("Попытка удалить несуществующий регион с id: " + id);
        }
    }

}
