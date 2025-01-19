package ru.permyakova.lab7_2.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dungeon")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dungeon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "d_id")
    private Integer id;

    @Column(name = "d_name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "d_region", nullable = false)
    @JsonBackReference
    private Region region;

    @Override
    public String toString() {
        return "Dungeon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", regionId=" + (region != null ? region.getId() : "null") +
                '}';
    }
}
