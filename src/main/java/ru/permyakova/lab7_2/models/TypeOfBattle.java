package ru.permyakova.lab7_2.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "type_of_battle")
@Data
public class TypeOfBattle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tyb_id")
    private Integer id;

    @Column(name = "tyb_name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "typeOfBattle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Character> characters;
}

