package recource.com.example.courier;

import org.apache.commons.lang3.RandomStringUtils;

public class GenerateCourier {

    public static Courier getCourier() {
        return Courier.builder()
                .login(RandomStringUtils.randomAlphabetic(8))
                .password(RandomStringUtils.randomAlphabetic(8))
                .firstname(RandomStringUtils.randomAlphabetic(8))
                .build();
    }
}
