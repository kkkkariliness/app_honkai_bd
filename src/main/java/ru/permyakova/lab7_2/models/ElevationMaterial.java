package ru.permyakova.lab7_2.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "elevation_material")
@Data
public class ElevationMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "em_id")
    private Integer id;

    @Column(name = "em_name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "elevationMaterial", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Character> characters;
}

