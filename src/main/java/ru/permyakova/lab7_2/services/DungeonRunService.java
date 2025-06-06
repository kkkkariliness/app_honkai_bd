package ru.permyakova.lab7_2.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.permyakova.lab7_2.models.DungeonRun;
import ru.permyakova.lab7_2.repositories.DungeonRunRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class DungeonRunService {
    private final DungeonRunRepository dungeonRunRepository;

    /**
     * Генерирует случайное время в пределах 1 часа.
     * @return Случайное время типа LocalTime.
     */
    public LocalTime generateRandomTime() {
        Random random = new Random();
        int hours = random.nextInt(2); // Максимум 1 час (0 или 1)
        int minutes = random.nextInt(60);
        int seconds = random.nextInt(60);
        return LocalTime.of(hours, minutes, seconds);
    }

    /**
     * Получает список всех прохождений подземелий, отсортированных по ID.
     * @return Список всех объектов DungeonRun.
     */
    @Transactional(readOnly = true)
    public List<DungeonRun> getAllDungeonRuns() {
        return dungeonRunRepository.findAll(Sort.by("id"));
    }

    /**
     * Получает прохождение подземелья по его уникальному идентификатору.
     * @param id Уникальный идентификатор прохождения подземелья.
     * @return Optional, содержащий объект DungeonRun, если он найден, иначе пустой Optional.
     */
    @Transactional(readOnly = true)
    public Optional<DungeonRun> getDungeonRunById(long id) {
        return dungeonRunRepository.findById(id);
    }

    /**
     * Добавляет новое прохождение подземелья в базу данных.
     * @param dungeonRun Объект DungeonRun для добавления.
     */
    @Transactional
    public void addDungeonRun(DungeonRun dungeonRun) {
        dungeonRunRepository.save(dungeonRun);
    }

    /**
     * Обновляет информацию о существующем прохождении подземелья.
     * @param id Уникальный идентификатор прохождения подземелья, которое нужно обновить.
     * @param dungeonRunDetails Объект DungeonRun с обновленными данными.
     * @throws IllegalArgumentException если прохождение подземелья с указанным ID не найдено.
     */
    @Transactional
    public void updateDungeonRun(long id, DungeonRun dungeonRunDetails) {
        DungeonRun dungeonRun = dungeonRunRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Нет похода в данж с id: " + id));
        dungeonRun.setCharacter(dungeonRunDetails.getCharacter());
        dungeonRun.setTimeScore(dungeonRunDetails.getTimeScore());
        dungeonRunRepository.save(dungeonRun);
    }

    /**
     * Удаляет прохождение подземелья из базы данных по его уникальному идентификатору.
     * @param id Уникальный идентификатор прохождения подземелья, которое нужно удалить.
     */
    @Transactional
    public void deleteDungeonRun(long id) {
        dungeonRunRepository.deleteById(id);
    }
}