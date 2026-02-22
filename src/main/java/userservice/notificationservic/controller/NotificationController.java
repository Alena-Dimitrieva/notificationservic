package userservice.notificationservic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import userservice.notificationservic.service.EmailService;
import userservice.notificationservic.event.OperationType;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final EmailService emailService;

    /**
     * API для ручной отправки уведомления
     */
    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(
            @RequestParam(name = "email", required = true) String email,
            @RequestParam(name = "operation", required = true) String operation) {

        OperationType op;
        try {
            op = OperationType.valueOf(operation.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body("Ошибка: неизвестная операция '" + operation + "'");
        }

        String message = emailService.buildMessage(op);
        emailService.sendEmail(email, message);

        return ResponseEntity.ok("Письмо отправлено: " + message);
    }
}