package ru.permyakova.lab7_2.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "way")
@Data
public class Way {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "w_id")
    private Integer id;

    @Column(name = "w_name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "way", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Character> characters;

    @OneToMany(mappedBy = "way", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LightCone> lightCones;
}

