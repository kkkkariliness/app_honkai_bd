package ru.permyakova.lab7_2.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.permyakova.lab7_2.models.Dungeon;
import ru.permyakova.lab7_2.models.Region;
import ru.permyakova.lab7_2.repositories.DungeonRepository;
import ru.permyakova.lab7_2.repositories.RegionRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DungeonService {
    private final DungeonRepository dungeonRepository;
    private final RegionRepository regionRepository;

    // TODO: Получить список всех подземелий, отсортированных по имени
    /**
     * Получает список всех подземелий, отсортированных по имени.
     * @return Список всех подземелий.
     */
    @Transactional(readOnly = true)
    public List<Dungeon> getAllDungeons() {
        List<Dungeon> dungeons = dungeonRepository.findAll(Sort.by("name"));
        return dungeons;
    }

    // TODO: Получить список подземелий по идентификатору региона
    /**
     * Получает список подземелий по идентификатору региона.
     * @param regionId Идентификатор региона.
     * @return Список подземелий, принадлежащих указанному региону.
     */
    public List<Dungeon> getDungeonsByRegion(Integer regionId) {
        return dungeonRepository.findByRegionId(regionId);
    }

    // TODO: Получить подземелье по его уникальному идентификатору
    /**
     * Получает подземелье по его уникальному идентификатору.
     * @param id Уникальный идентификатор подземелья.
     * @return Optional, содержащий подземелье, если оно найдено, иначе пустой Optional.
     */
    @Transactional(readOnly = true)
    public Optional<Dungeon> getDungeonById(long id) {
        return dungeonRepository.findById(id);
    }

    // TODO: Добавить новое подземелье в базу данных
    /**
     * Добавляет новое подземелье в базу данных.
     * @param dungeon Объект подземелья для добавления.
     */
    @Transactional
    public void addDungeon(Dungeon dungeon) {
        dungeonRepository.save(dungeon);
    }

    // TODO: Обновить информацию о существующем подземелье
    /**
     * Обновляет информацию о существующем подземелье.
     * @param id ID подземелья для обновления.
     * @param updatedDungeon Объект с новыми данными (например, имя).
     * @param newRegionId ID нового региона.
     * @throws IllegalArgumentException если подземелье или регион не найдены.
     */
    @Transactional
    public void updateDungeon(long id, Dungeon updatedDungeon, long newRegionId) {
        // 1. Находим подземелье, которое хотим обновить, по его ID.
        Dungeon dungeonToUpdate = dungeonRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Подземелье с id " + id + " не найдено"));

        // 2. Находим новый регион по его ID.
        Region newRegion = regionRepository.findById(newRegionId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Регион с id " + newRegionId + " не найден"));

        // 3. Обновляем поля существующего подземелья.
        dungeonToUpdate.setName(updatedDungeon.getName()); // Обновляем имя
        dungeonToUpdate.setRegion(newRegion); // Устанавливаем новый регион

        // 4. Сохраняем обновленное подземелье.
        // Явный вызов save() не обязателен в транзакционном методе,
        // но делает код более понятным.
        dungeonRepository.save(dungeonToUpdate);
    }

    // TODO: Удалить подземелье из базы данных по его уникальному идентификатору
    /**
     * Удаляет подземелье из базы данных по его уникальному идентификатору.
     * @param id Уникальный идентификатор подземелья, которое нужно удалить.
     */
    @Transactional
    public void deleteDungeon(long id) {
        dungeonRepository.deleteById(id);
    }
}