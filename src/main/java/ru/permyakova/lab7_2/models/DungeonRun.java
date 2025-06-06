package ru.permyakova.lab7_2.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;

/**
 * Сущность, представляющая запись о прохождении данжа.
 * Хранит информацию о том, какой данж был пройден, каким персонажем,
 * и за какое время.
 */
@Entity
@Table(name = "dungeon_run")
@Data
public class DungeonRun {

    /**
     * Уникальный идентификатор записи о прохождении данжа.
     * Генерируется автоматически при создании.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "d_id")
    private Integer id;

    /**
     * Данж, который было пройдено.
     * Это отношение "многие к одному" (ManyToOne) с сущностью Dungeon.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "d_dungeon_id", nullable = false)
    private Dungeon dungeon;

    /**
     * Персонаж, который прошел данж.
     * Это отношение "многие к одному" (ManyToOne) с сущностью Character.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "d_character", nullable = false)
    private Character character;

    /**
     * Время, за которое был пройден данж.
     * Генерируется рандомно.
     */
    @Column(name = "d_time_score", nullable = false)
    private LocalTime timeScore;

    /**
     * Возвращает строковое представление объекта DungeonRun.
     * @return Строковое представление записи о прохождении данжа.
     */
    @Override
    public String toString() {
        return "DungeonRun{" +
                "id=" + id +
                ", dungeonId=" + (dungeon != null ? dungeon.getId() : "null") +
                ", characterId=" + (character != null ? character.getId() : "null") +
                ", timeScore=" + timeScore +
                '}';
    }
}