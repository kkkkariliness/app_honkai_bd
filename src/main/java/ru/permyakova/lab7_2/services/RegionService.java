package ru.permyakova.lab7_2.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.permyakova.lab7_2.models.Region;
import ru.permyakova.lab7_2.repositories.RegionRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegionService {
    private final RegionRepository regionRepository;

    /**
     * Получает список всех регионов, отсортированных по имени.
     * @return Список всех регионов.
     */
    @Transactional(readOnly = true)
    public List<Region> getAllRegions() {
        List<Region> regions = regionRepository.findAll(Sort.by("name"));
        log.info("Regions fetched: " + regions);
        return regions;
    }

    /**
     * Добавляет новый регион в базу данных.
     * @param region Объект региона для добавления.
     */
    @Transactional
    public void addRegion(Region region) {
        regionRepository.save(region);
    }

    /**
     * Обновляет информацию о существующем регионе.
     * @param id Уникальный идентификатор региона, который нужно обновить.
     * @param regionDetails Объект региона с обновленными данными.
     * @throws IllegalArgumentException если регион с указанным Id не найден.
     */
    @Transactional
    public void updateRegion(long id, Region regionDetails) {
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid region Id: " + id));
        region.setName(regionDetails.getName());
        regionRepository.save(region);
    }

    /**
     * Получает список регионов по имени. Если имя не указано, возвращает все регионы.
     * @param name Имя региона для поиска (может быть null).
     * @return Список регионов, соответствующих имени, или все регионы, если имя не указано.
     */
    @Transactional
    public List<Region> getRegion(String name) {
        if (name != null) {
            return regionRepository.findByName(name);
        }
        return regionRepository.findAll();
    }

    /**
     * Сохраняет или обновляет регион в базе данных.
     * @param region Объект региона для сохранения или обновления.
     */
    @Transactional
    public void saveRegion(Region region) {
        log.info("Saving/updating region: " + region);
        regionRepository.save(region);
    }

    /**
     * Удаляет регион из базы данных по его уникальному идентификатору.
     * Осуществляет каскадное удаление связанных сущностей (например, подземелий).
     * @param id Уникальный идентификатор региона, который нужно удалить.
     */
    @Transactional
    public void deleteRegion(long id) {
        if (regionRepository.existsById(id)) {
            regionRepository.deleteById(id); // Каскадное удаление данжей
        } else {
            log.warn("Попытка удалить несуществующий регион с id: " + id);
        }
    }

    /**
     * Получает регион по его уникальному идентификатору.
     * @param id Уникальный идентификатор региона.
     * @return Optional, содержащий регион, если он найден, иначе пустой Optional.
     */
    @Transactional(readOnly = true)
    public Optional<Region> getRegionById(long id) {
        return regionRepository.findById(id);
    }
}