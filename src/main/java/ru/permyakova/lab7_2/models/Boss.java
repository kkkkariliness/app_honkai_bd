package ru.permyakova.lab7_2.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "boss")
@Data
public class Boss {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "b_id")
    private Integer id;

    @Column(name = "b_name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "boss", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Character> characters;
}

