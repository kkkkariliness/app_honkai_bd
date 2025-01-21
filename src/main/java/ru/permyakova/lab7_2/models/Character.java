package ru.permyakova.lab7_2.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "character")
@Data
public class Character {

    @Id
    @GeneratedValue
    @Column(name = "ch_id", unique = true, nullable = false)
    private UUID id;

    @NotBlank(message = "Имя персонажа не может быть пустым")
    @Size(max = 255, message = "Имя персонажа не может превышать 255 символов")
    @Column(name = "ch_name", nullable = false)
    private String name;

    @Min(value = 4, message = "Редкость персонажа должна быть не меньше 4")
    @Max(value = 5, message = "Редкость персонажа должна быть не больше 5")
    @Column(name = "ch_rarity", nullable = false)
    private short rarity;

    @NotBlank(message = "Путь персонажа обязателен")
    @Size(max = 50, message = "Путь персонажа не может превышать 50 символов")
    @Column(name = "ch_way", nullable = false)
    private String way;

    @NotBlank(message = "Тип боя обязателен")
    @Size(max = 50, message = "Тип боя не может превышать 50 символов")
    @Column(name = "ch_type_of_battle", nullable = false)
    private String typeOfBattle;

    @NotNull(message = "Дата встречи обязательна")
    @Column(name = "ch_date_of_meet", nullable = false)
    private LocalDate dateOfMeet;

    @Column(name = "ch_is_alive", nullable = false)
    private boolean isAlive;

    @NotNull(message = "Сумма доната обязательна")
    @DecimalMin(value = "0.0", inclusive = true, message = "Сумма доната должна быть неотрицательной")
    @Column(name = "ch_money_donat", nullable = false)
    private BigDecimal moneyDonat;

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
