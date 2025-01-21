package ru.permyakova.lab7_2.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "region")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reg_id")
    private Integer id;

    @NotBlank(message = "Название региона не может быть пустым")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]+$", message = "Название региона может содержать только буквы")
    @Column(name = "reg_name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Dungeon> dungeons = new ArrayList<>();

    @Override
    public String toString() {
        return "Region{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public List<Dungeon> getDungeons() {
        return dungeons != null ? dungeons : Collections.emptyList();
    }
}
