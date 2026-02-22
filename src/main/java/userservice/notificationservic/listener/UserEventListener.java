package userservice.notificationservic.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import userservice.notificationservic.event.UserEvent;
import userservice.notificationservic.service.EmailService;

@Component
@RequiredArgsConstructor
public class UserEventListener {

    private final EmailService emailService;

    /**
     * Слушает события UserService из Kafka
     */
    @KafkaListener(
            topics = "user-events",
            groupId = "notification-servic-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(UserEvent event) {
        if (event == null || event.getEmail() == null || event.getOperation() == null) {
            System.out.println("Получено некорректное событие: " + event);
            return;
        }

        String message = emailService.buildMessage(event.getOperation());
        emailService.sendEmail(event.getEmail(), message);
    }
}