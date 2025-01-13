package ru.permyakova.lab7_2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "ru.permyakova.lab7_2.*")
public class Lab72Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab72Application.class, args);
    }

}
