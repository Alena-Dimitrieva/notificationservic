package userservice.notificationservic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class NotificationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testSendNotificationCreated() throws Exception {
        mockMvc.perform(post("/notifications/send")
                        .param("email", "test@example.com")
                        .param("operation", "CREATED"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Письмо отправлено")));
    }

    @Test
    void testSendNotificationDeleted() throws Exception {
        mockMvc.perform(post("/notifications/send")
                        .param("email", "test@example.com")
                        .param("operation", "DELETED"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Письмо отправлено")));
    }

    @Test
    void testSendNotificationUnknown() throws Exception {
        mockMvc.perform(post("/notifications/send")
                        .param("email", "test@example.com")
                        .param("operation", "UNKNOWN"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Ошибка")));
    }
}