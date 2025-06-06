package ru.permyakova.lab7_2.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Сущность, представляющая персонажа в игре.
 * Хранит информацию о персонаже, такую как имя, редкость, путь, тип боя,
 * дату встречи, жив ли персонаж по сюжету и сумму доната.
 */
@Entity
@Table(name = "character")
@Data
public class Character {

    /**
     * Уникальный идентификатор персонажа.
     * Генерируется автоматически.
     */
    @Id
    @GeneratedValue
    @Column(name = "ch_id", unique = true, nullable = false)
    private UUID id;

    /**
     * Имя персонажа.
     * Не может быть пустым, длина до 255 символов, содержит только буквы и пробелы.
     */
    @NotBlank(message = "Имя персонажа не может быть пустым")
    @Size(max = 255, message = "Имя персонажа не может превышать 255 символов")
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯёЁ\\s]+$",
            message = "Имя персонажа может содержать только буквы и пробелы")
    @Column(name = "ch_name", nullable = false)
    private String name;

    /**
     * Редкость персонажа.
     * Должна быть в диапазоне от 4 до 5.
     */
    @NotNull(message = "Редкость персонажа не может быть пустой")
    @Min(value = 4, message = "Редкость персонажа должна быть не меньше 4")
    @Max(value = 5, message = "Редкость персонажа должна быть не больше 5")
    @Column(name = "ch_rarity", nullable = false)
    private Short rarity;

    /**
     * Путь персонажа (ПРИМ: Сохранение, Изобилие и т.д.).
     * Обязателен, длина до 50 символов, содержит только буквы и пробелы.
     */
    @NotBlank(message = "Путь персонажа обязателен")
    @Size(max = 50, message = "Путь персонажа не может превышать 50 символов")
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯёЁ\\s]+$",
            message = "Путь персонажа может содержать только буквы и пробелы")
    @Column(name = "ch_way", nullable = false)
    private String way;

    /**
     * Тип боя персонажа (ПРИМ: Мнимый, Ледяной и т.д.).
     * Обязателен, длина до 50 символов, содержит только буквы и пробелы.
     */
    @NotBlank(message = "Тип боя обязателен")
    @Size(max = 50, message = "Тип боя не может превышать 50 символов")
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯёЁ\\s]+$",
            message = "Тип боя может содержать только буквы и пробелы")
    @Column(name = "ch_type_of_battle", nullable = false)
    private String typeOfBattle;

    /**
     * Дата встречи с персонажем.
     * Обязательна.
     */
    @NotNull(message = "Дата встречи обязательна")
    @Column(name = "ch_date_of_meet", nullable = false)
    private LocalDate dateOfMeet;

    /**
     * Жив ли персонаж по сюжету (да - нет).
     */
    @Column(name = "ch_is_alive")
    private boolean isAlive;

    /**
     * Сумма, задоначенная на персонажа.
     * Должна быть неотрицательной.
     */
    @NotNull(message = "Сумма доната обязательна")
    @DecimalMin(value = "0.0", inclusive = true,
            message = "Сумма доната должна быть неотрицательной")
    @Column(name = "ch_money_donat", nullable = false)
    private BigDecimal moneyDonat;

    /**
     * Возвращает строковое представление объекта Character.
     * @return Строковое представление персонажа.
     */
    @Override
    public String toString() {
        return "Character{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rarity=" + rarity +
                ", way='" + way + '\'' +
                ", typeOfBattle='" + typeOfBattle + '\'' +
                ", dateOfMeet=" + dateOfMeet +
                ", isAlive=" + isAlive +
                ", moneyDonat=" + moneyDonat +
                '}';
    }
}