package ru.permyakova.lab7_2.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;

/**
 * Сущность, представляющая запись о прохождении подземелья.
 * Хранит информацию о том, какое подземелье было пройдено, каким персонажем,
 * и за какое время.
 */
@Entity
@Table(name = "dungeon_run")
@Data
public class DungeonRun {

    /**
     * Уникальный идентификатор записи о прохождении подземелья.
     * Генерируется автоматически при создании.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "d_id")
    private Integer id;

    /**
     * Подземелье, которое было пройдено.
     * Это отношение "многие к одному" (ManyToOne) с сущностью Dungeon.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "d_dungeon_id", nullable = false)
    private Dungeon dungeon;

    /**
     * Персонаж, который прошел подземелье.
     * Это отношение "многие к одному" (ManyToOne) с сущностью Character.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "d_character", nullable = false)
    private Character character;

    /**
     * Время, за которое было пройдено подземелье.
     * Обязательно для заполнения.
     */
    @NotNull(message = "Время прохождения обязательно")
    @Column(name = "d_time_score", nullable = false)
    private LocalTime timeScore;

    /**
     * Возвращает строковое представление объекта DungeonRun.
     * @return Строковое представление записи о прохождении подземелья.
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