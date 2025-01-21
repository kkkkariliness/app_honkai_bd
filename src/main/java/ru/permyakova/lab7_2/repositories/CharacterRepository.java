package ru.permyakova.lab7_2.repositories;

import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.permyakova.lab7_2.models.Character;
import java.math.BigDecimal;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CharacterRepository extends JpaRepository<Character, UUID> {
    // Найти всех персонажей с донатами больше N
    List<Character> findAllByMoneyDonatGreaterThan(BigDecimal moneyDonat);

    // Найти общую сумму донатов живых персонажей
    @Query("SELECT SUM(c.moneyDonat) FROM Character c WHERE c.isAlive = true")
    BigDecimal sumDonationsOfAliveCharacters();

    // Найти случайного персонажа с донатами больше N
    @Query(value = "SELECT * FROM character c WHERE c.ch_money_donat > :moneyDonat LIMIT 1", nativeQuery = true)
    Optional<Character> findRandomCharacterByMoneyDonatGreaterThan(@Param("moneyDonat") BigDecimal moneyDonat);
}

