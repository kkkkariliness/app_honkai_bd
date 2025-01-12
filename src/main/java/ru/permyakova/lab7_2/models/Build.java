package ru.permyakova.lab7_2.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "build")
@Data
public class Build {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "as_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "as_id_of_character", nullable = false)
    private Character character;

    @ManyToOne
    @JoinColumn(name = "as_id_of_light_cone")
    private LightCone lightCone;

    @ManyToOne
    @JoinColumn(name = "as_id_of_relic")
    private Relic relic;

    @ManyToOne
    @JoinColumn(name = "as_id_of_food")
    private Food food;
}

