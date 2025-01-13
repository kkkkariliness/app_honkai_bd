package ru.permyakova.lab7_2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.permyakova.lab7_2.models.Character;

import java.util.UUID;

public interface CharacterRepository extends JpaRepository<Character, UUID> {
}
