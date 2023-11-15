package createCourier;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Courier {
    private String login;
    private String password;
    private String firstname;
}
