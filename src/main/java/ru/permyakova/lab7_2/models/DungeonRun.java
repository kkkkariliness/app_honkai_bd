package ru.permyakova.lab7_2.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;

@Entity
@Table(name = "dungeon_run")
@Data
public class DungeonRun {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "d_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "d_dungeon_id", nullable = false)
    private Dungeon dungeon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "d_character", nullable = false)
    private Character character;

    @NotNull(message = "Время прохождения обязательно")
    @Column(name = "d_time_score", nullable = false)
    private LocalTime timeScore;

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
