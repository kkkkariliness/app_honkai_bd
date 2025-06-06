package ru.permyakova.lab7_2.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Сущность, представляющая подземелье в игре.
 * Хранит информацию о подземелье, такую как его название и регион, к которому оно принадлежит.
 */
@Entity
@Table(name = "dungeon")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dungeon {

    /**
     * Уникальный идентификатор подземелья.
     * Генерируется автоматически при создании.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "d_id")
    private Integer id;

    /**
     * Название подземелья.
     * Не может быть пустым и может содержать только буквы.
     */
    @NotBlank(message = "Название данжа не может быть пустым")
    @Column(name = "d_name", nullable = false)
    private String name;

    /**
     * Регион, к которому принадлежит подземелье.
     * Это отношение "многие к одному" (ManyToOne) с сущностью Region.
     * {@link JsonBackReference} используется для предотвращения зацикливания при сериализации JSON.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "d_region", nullable = false)
    @JsonBackReference
    private Region region;

    /**
     * Возвращает строковое представление объекта Dungeon.
     * @return Строковое представление подземелья, включая его ID, название и ID связанного региона.
     */
    @Override
    public String toString() {
        return "Dungeon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", regionId=" + (region != null ? region.getId() : "null") + '}';
    }
}