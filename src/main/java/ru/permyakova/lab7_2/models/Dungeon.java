package ru.permyakova.lab7_2.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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

    @NotBlank(message = "Название данжа не может быть пустым")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]+$", message = "Название может содержать только буквы")
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
