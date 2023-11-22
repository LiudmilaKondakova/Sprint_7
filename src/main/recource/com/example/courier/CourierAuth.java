package recource.com.example.courier;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CourierAuth {
    private String login;
    private String password;

    public CourierAuth(Courier courier) {
        this.login = courier.getLogin();
        this.password = courier.getPassword();
    }
}
