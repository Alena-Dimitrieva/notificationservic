package userservice.notificationservic.service;

import org.springframework.stereotype.Service;
import userservice.notificationservic.event.OperationType;

@Service
public class EmailService {

    /**
     * Метод отправки письма
     */
    public void sendEmail(String to, String text) {
        System.out.println("=== Отправка письма ===");
        System.out.println("Кому: " + to);
        System.out.println("Текст: " + text);
        System.out.println("=======================");
    }

    /**
     * Формирует текст письма в зависимости от операции
     */
    public String buildMessage(OperationType operation) {
        if (operation == OperationType.CREATED) {
            return "Здравствуйте! Ваш аккаунт на сайте ваш сайт был успешно создан.";
        } else if (operation == OperationType.DELETED) {
            return "Здравствуйте! Ваш аккаунт был удалён.";
        } else {
            return "Неизвестная операция: " + operation;
        }
    }
}
