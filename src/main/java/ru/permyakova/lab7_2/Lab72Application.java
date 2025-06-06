package ru.permyakova.lab7_2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * Главный класс Spring Boot приложения.
 * Отвечает за запуск приложения.
 */
@SpringBootApplication
@EntityScan(basePackages = "ru.permyakova.lab7_2.*")
public class Lab72Application {

    /**
     * Основной метод для запуска Spring Boot приложения.
     * @param args Аргументы командной строки, передаваемые при запуске приложения.
     */
    public static void main(String[] args) {
        SpringApplication.run(Lab72Application.class, args);
    }

}