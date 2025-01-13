package ru.permyakova.lab7_2.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    @Column(name = "ch_way", nullable = false)
    private String way;

    @Column(name = "ch_type_of_battle", nullable = false)
    private String typeOfBattle;

    @Column(name = "ch_date_of_meet", nullable = false)
    private LocalDate dateOfMeet;

    @Column(name = "ch_is_alive", nullable = false)
    private boolean isAlive;

    @Column(name = "ch_money_donat", nullable = false)
    private BigDecimal moneyDonat;
}

