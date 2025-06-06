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

    // TODO: Получить список всех регионов, отсортированных по имени
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

    // TODO: Добавить новый регион в базу данных
    /**
     * Добавляет новый регион в базу данных.
     * @param region Объект региона для добавления.
     */
    @Transactional
    public void addRegion(Region region) {
        regionRepository.save(region);
    }

    // TODO: Обновить информацию о существующем регионе
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

    // TODO: Сохранить или обновить регион в базе данных
    /**
     * Сохраняет или обновляет регион в базе данных.
     * @param region Объект региона для сохранения или обновления.
     */
    @Transactional
    public void saveRegion(Region region) {
        log.info("Saving/updating region: " + region);
        regionRepository.save(region);
    }

    // TODO: Удалить регион из базы данных по его уникальному идентификатору с каскадным удалением
    /**
     * Удаляет регион из базы данных по его уникальному идентификатору.
     * Осуществляет каскадное удаление связанных данжей.
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

    // TODO: Получить регион по его уникальному идентификатору
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