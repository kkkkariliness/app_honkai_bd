package ru.permyakova.lab7_2.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Сущность, представляющая регион в игре.
 * Хранит информацию о регионе, такую как его уникальный идентификатор и название.
 * Также управляет списком данжей, расположенных в этом регионе.
 */
@Entity
@Table(name = "region")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Region {

    /**
     * Уникальный идентификатор региона.
     * Генерируется автоматически при создании.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reg_id")
    private Integer id;

    /**
     * Название региона.
     * Не может быть пустым.
     */
    @NotBlank(message = "Название региона не может быть пустым")
    @Column(name = "reg_name", nullable = false)
    private String name;

    /**
     * Список данжей, принадлежащих этому региону.
     * Это отношение "один ко многим" (OneToMany) с сущностью Dungeon.
     * При удалении региона, связанные данжи также будут удалены
     * (cascade = CascadeType.ALL, orphanRemoval = true).
     * {@link JsonManagedReference} используется для управления
     * сериализацией JSON и предотвращения зацикливания.
     */
    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Dungeon> dungeons = new ArrayList<>();

    /**
     * Возвращает строковое представление объекта Region.
     * @return Строковое представление региона, включая его ID и название.
     */
    @Override
    public String toString() {
        return "Region{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    /**
     * Возвращает список данжей, связанных с этим регионом.
     * Если список данжей равен null, возвращает пустой неизменяемый список.
     * @return Список объектов Dungeon.
     */
    public List<Dungeon> getDungeons() {
        return dungeons != null ? dungeons : Collections.emptyList();
    }
}