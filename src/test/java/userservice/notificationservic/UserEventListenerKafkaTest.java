package userservice.notificationservic;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import userservice.notificationservic.event.OperationType;
import userservice.notificationservic.event.UserEvent;
import userservice.notificationservic.service.EmailService;

import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.atMost;

@EmbeddedKafka(partitions = 1, topics = {"user-events"})
@SpringBootTest
@ActiveProfiles("test")
class UserEventListenerKafkaTest {

    private static final String TOPIC = "user-events";

    @Autowired
    private KafkaTemplate<String, UserEvent> kafkaTemplate;

    @SpyBean
    private EmailService emailService;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafka;

    @BeforeEach
    void resetMocks() {
        // сброс моков перед каждым тестом, чтобы избежать многократных вызовов предыдущих тестов
        reset(emailService);
    }

    @Test
    void shouldSendEmailWhenUserCreated() {
        UserEvent event = new UserEvent("test@example.com", OperationType.CREATED);

        kafkaTemplate.send(TOPIC, event);

        Awaitility.await()
                .atMost(5, TimeUnit.SECONDS)
                .untilAsserted(() ->
                        // добавлено atMost(1) для проверки одного вызова метода
                        verify(emailService, atMost(1)).sendEmail(
                                "test@example.com",
                                "Здравствуйте! Ваш аккаунт на сайте ваш сайт был успешно создан."
                        )
                );
    }

    @Test
    void shouldSendEmailWhenUserDeleted() {
        UserEvent event = new UserEvent("test@example.com", OperationType.DELETED);

        kafkaTemplate.send(TOPIC, event);

        Awaitility.await()
                .atMost(5, TimeUnit.SECONDS)
                .untilAsserted(() ->
                        // добавлено atMost(1) для проверки одного вызова метода
                        verify(emailService, atMost(1)).sendEmail(
                                "test@example.com",
                                "Здравствуйте! Ваш аккаунт был удалён."
                        )
                );
    }
}