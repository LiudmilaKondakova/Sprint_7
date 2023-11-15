package createCourier;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CourierAuthWithoutLogin {
    private String password;

    public CourierAuthWithoutLogin(Courier courier) {
        this.password = courier.getPassword();
    }
}
