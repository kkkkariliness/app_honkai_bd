package ru.permyakova.lab7_2.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "light_cone")
@Data
public class LightCone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lc_id")
    private Integer id;

    @Column(name = "lc_name", nullable = false)
    private String name;

    @Column(name = "lc_raryti", nullable = false)
    private short rarity;

    @ManyToOne
    @JoinColumn(name = "lc_way", nullable = false)
    private Way way;

    @Column(name = "lc_baff", nullable = false)
    private String buff;

    @OneToMany(mappedBy = "lightCone", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Build> builds;
}

