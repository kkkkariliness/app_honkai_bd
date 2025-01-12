package ru.permyakova.lab7_2.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "dungeon")
@Data
public class Dungeon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "d_id")
    private Integer id;

    @Column(name = "d_name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "d_region", nullable = false)
    private Region region;

    @OneToMany(mappedBy = "dungeon", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DungeonRun> dungeonRuns;
}

