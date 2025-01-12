package ru.permyakova.lab7_2.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "trace_pumping_material")
@Data
public class TracePumpingMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tpm_id")
    private Integer id;

    @Column(name = "tpm_name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "tracePumpingMaterial", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Character> characters;
}

