package userservice.notificationservic.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor  // нужен для десериализации JSON (Kafka)
@AllArgsConstructor
public class UserEvent {

    private String email;
    private OperationType operation;

}