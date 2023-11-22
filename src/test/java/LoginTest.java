import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.Test;
import recource.com.example.courier.*;

import java.util.Objects;

import static java.lang.Integer.MAX_VALUE;
import static org.hamcrest.CoreMatchers.*;
import static recource.com.example.courier.MyComponent.*;

@Slf4j
public class LoginTest{


    @Test
    @DisplayName("Авторизация")
    public void courierLogin() {
        getResponse("", "").then().statusCode(200);
    }

    @Test
    @DisplayName("Авторизация без поля login")
    public void courierWithoutLogin() {
        getResponse("login", "").then().statusCode(400).and().body(FIELD_MESSAGE, equalTo(MESSAGE_NO_ENOUGH_DATA));
    }

    @Test
    @DisplayName("Авторизация без поля password")
    public void courierWithoutPassword() {
        getResponse("password", "").then().statusCode(400).and().body(FIELD_MESSAGE, Matchers.equalTo(MESSAGE_NO_ENOUGH_DATA));
    }

    @Test
    @DisplayName("Авторизация login = null")
    public void courierWithLoginNull() {
        getResponse("login", null).then().statusCode(400).and().body(FIELD_MESSAGE, Matchers.equalTo(MESSAGE_NO_ENOUGH_DATA));
    }

    @Test
    @DisplayName("Авторизация password = null")
    public void courierWithPasswordNull() {  // Тест падает
        getResponse("password", null).then().statusCode(400).and().body(FIELD_MESSAGE, Matchers.equalTo(MESSAGE_NO_ENOUGH_DATA));
    }

    @Test
    @DisplayName("Авторизация с неверным login и password")
    public void courierNoExist() {
        getResponse("all", "").then().statusCode(404).and().body(FIELD_MESSAGE, equalTo(MESSAGE_COURIER_NOT_FOUND));
    }

    private Response getResponse(String key, String data) {
        Courier courier = GenerateCourier.getCourier();
        log.info(CREATE_COURIER, courier);
        Response response = RequestApi.post(courier, ROOT);
        CourierAuth courierAuth = new CourierAuth(courier);
        if(Objects.equals(key, "login")){
            courierAuth.setLogin(data);
        } else if (Objects.equals(key, "password")) {
            courierAuth.setPassword(data);
        } else if (Objects.equals(key, "all")) {
            courierAuth.setLogin(String.valueOf(MAX_VALUE));
            courierAuth.setPassword(String.valueOf(MAX_VALUE));
        }
        log.info(COURIER_AUTHORIZATION, courierAuth);
        Response responseAuth = RequestApi.post(courierAuth, ROOT + LOGIN);
        log.info(RESPONSE + "\n", responseAuth.body().asString());
        return responseAuth;
    }
}
