package ru.permyakova.lab7_2.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActionService {
    private final PlatformTransactionManager transactionManager;
    private TransactionStatus transactionStatus;

    // Создаем транзакцию, если текущая транзакция отсутствует или уже завершена
    public void saveCheckpoint() {
        if (transactionStatus == null || transactionStatus.isCompleted()) {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition(); // Создание
            def.setName("Transaction"); // Даем ей имя
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            transactionStatus = transactionManager.getTransaction(def); // Начинаем транзакцию
            log.info("Точка сохранения установлена");
        } else {
            log.warn("Точка сохранения уже установлена. Пропускаем");
        }
    }

    // Фиксируем текущую транзакцию
    public void commitCheckpoint() {
        if (transactionStatus != null && !transactionStatus.isCompleted()) {
            transactionManager.commit(transactionStatus);
            transactionStatus = null;
            log.info("Транзакция закоммичена");
        } else {
            log.warn("Нет активной транзакции");
        }
    }

    // Откатываем транзакцию на её начало
    public void performRollbackToLastCheckpoint() {
        if (transactionStatus != null && !transactionStatus.isCompleted()) {
            transactionManager.rollback(transactionStatus);
            transactionStatus = null;
            log.info("Откат на последнюю точку сохранения");
        } else {
            log.warn("Нет точек сохранения");
        }
    }
}
