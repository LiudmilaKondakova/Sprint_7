package createCourier;

import org.apache.commons.lang3.RandomStringUtils;

public class GenerateCourier {
    private final String password = "password";

    public Courier getCourier() {
        return Courier.builder()
                .login(RandomStringUtils.randomAlphabetic(8))
                .password(RandomStringUtils.randomAlphabetic(8))
                .firstname(RandomStringUtils.randomAlphabetic(8))
                .build();
    }

    public CourierWithoutPassword getCourierWithoutPassword() {
        return new CourierWithoutPassword(RandomStringUtils.randomAlphabetic(8));
    }

    public Courier getCourierWithPasswordNull() {
        return Courier.builder()
                .password(password)
                .build();
    }

    public CourierWithoutLogin getCourierWithoutLogin() {
        return new CourierWithoutLogin(password);
    }

    public Courier getCourierWithLoginNull() {
        return Courier.builder()
                .password(password)
                .build();
    }

    public  CourierAuth getCourierAuth(Courier courier) {
        return CourierAuth.builder()
                .login(courier.getLogin())
                .password(courier.getPassword())
                .build();
    }

    public CourierAuthWithoutLogin getCourierAuthWithoutLogin(Courier courier) {
        return new CourierAuthWithoutLogin(courier);
    }

    public CourierAuthWithoutPassword getCourierAuthWithoutPassword(Courier courier) {
        return new CourierAuthWithoutPassword(courier);
    }

    public CourierAuth getCourierAuthWithLoginNull(Courier courier) {
        return CourierAuth.builder()
                .password(courier.getPassword())
                .build();
    }

    public CourierAuth getCourierAuthWithPasswordNull(Courier courier) {
        return CourierAuth.builder()
                .login(courier.getLogin())
                .build();
    }
}
