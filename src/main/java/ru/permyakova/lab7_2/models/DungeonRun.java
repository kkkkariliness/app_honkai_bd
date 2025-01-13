package ru.permyakova.lab7_2.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;

@Entity
@Table(name = "dungeon_run")
@Data
public class DungeonRun {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "d_id" )
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "d_dungeon_id", nullable = false)
    private Dungeon dungeon;

    @ManyToOne
    @JoinColumn(name = "d_character", nullable = false)
    private Character character;

    @Column(name = "d_time_score", nullable = false)
    private LocalTime timeScore;
}
