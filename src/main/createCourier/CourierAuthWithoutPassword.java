package createCourier;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CourierAuthWithoutPassword {
    private String login;

    public CourierAuthWithoutPassword(Courier courier){
        this.login = courier.getLogin();
    }
}
