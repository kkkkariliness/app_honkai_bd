package ru.permyakova.lab7_2.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "character")
@Data
public class Character {
    @Id
    @GeneratedValue
    @Column(name = "ch_id")
    private UUID id;

    @Column(name = "ch_name", nullable = false)
    private String name;

    @Column(name = "ch_rarity", nullable = false)
    private short rarity;

    @ManyToOne
    @JoinColumn(name = "ch_way", nullable = false)
    private Way way;

    @ManyToOne
    @JoinColumn(name = "ch_type_of_battle", nullable = false)
    private TypeOfBattle typeOfBattle;

    @ManyToOne
    @JoinColumn(name = "ch_boss", nullable = false)
    private Boss boss;

    @ManyToOne
    @JoinColumn(name = "ch_elevation_material", nullable = false)
    private ElevationMaterial elevationMaterial;

    @ManyToOne
    @JoinColumn(name = "ch_trace_pumping_material", nullable = false)
    private TracePumpingMaterial tracePumpingMaterial;

    @Column(name = "ch_date_of_meet", nullable = false)
    private LocalDate dateOfMeet;

    @Column(name = "ch_is_alive", nullable = false)
    private boolean isAlive;

    @Column(name = "ch_money_donat", nullable = false)
    private BigDecimal moneyDonat;

    @OneToMany(mappedBy = "character", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Build> builds;
}

