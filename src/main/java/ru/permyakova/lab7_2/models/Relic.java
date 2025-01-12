package ru.permyakova.lab7_2.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "relic")
@Data
public class Relic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rel_id")
    private Integer id;

    @Column(name = "rel_name", nullable = false)
    private String name;

    @Column(name = "rel_baff", nullable = false)
    private String buff;

    @OneToMany(mappedBy = "relic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Build> builds;
}

