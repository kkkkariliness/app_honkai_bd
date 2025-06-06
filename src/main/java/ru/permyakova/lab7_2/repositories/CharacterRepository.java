package ru.permyakova.lab7_2.repositories;

import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.permyakova.lab7_2.models.Character;
import java.math.BigDecimal;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий для управления сущностями {@link Character}.
 * Предоставляет методы для выполнения операций CRUD и
 * пользовательские запросы к базе данных для персонажей.
 */
public interface CharacterRepository extends JpaRepository<Character, UUID> {
    // TODO: Найти всех персонажей с донатом больше указанного значения
    /**
     * Находит всех персонажей, у которых сумма доната превышает указанное значение.
     * @param moneyDonat Минимальная сумма доната для поиска.
     * @return Список персонажей, чья сумма доната больше заданной.
     */
    List<Character> findAllByMoneyDonatGreaterThan(BigDecimal moneyDonat);

    // TODO: Вычислить общую сумму донатов живых персонажей
    /**
     * Вычисляет общую сумму донатов всех живых персонажей.
     * @return Общая сумма донатов живых персонажей.
     */
    @Query("SELECT SUM(c.moneyDonat) FROM Character c WHERE c.isAlive = true")
    BigDecimal sumDonationsOfAliveCharacters();

    // TODO: Найти одного случайного персонажа с донатом больше указанного значения
    /**
     * Находит одного случайного персонажа, у которого сумма доната превышает указанное значение.
     * Использует нативный SQL-запрос для выбора случайного персонажа.
     * @param moneyDonat Минимальная сумма доната для поиска.
     * @return {@link Optional}, содержащий случайного персонажа,
     * если найден, иначе пустой {@link Optional}.
     */
    @Query(value = "SELECT * FROM character c " +
                    "WHERE c.ch_money_donat > :moneyDonat " +
                    "ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<Character> findRandomCharacterByMoneyDonatGreaterThan(
            @Param("moneyDonat") BigDecimal moneyDonat);
}