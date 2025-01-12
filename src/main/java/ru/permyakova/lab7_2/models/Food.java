package ru.permyakova.lab7_2.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "food")
@Data
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fd_id")
    private Integer id;

    @Column(name = "fd_name", nullable = false)
    private String name;

    @Column(name = "fd_baff", nullable = false)
    private String buff;

    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Build> builds;
}

