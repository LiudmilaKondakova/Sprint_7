import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import recource.com.example.courier.Courier;
import recource.com.example.courier.GenerateCourier;
import recource.com.example.courier.RequestApi;

import java.util.Objects;

import static org.hamcrest.CoreMatchers.equalTo;
import static recource.com.example.courier.MyComponent.*;

@Slf4j
public class CreateCourierTest{


    @Test
    @DisplayName("Создание курьера со всеми параметрами")
    public void createCourier() {
        getResponse("", "").then().assertThat().statusCode(201).and().body(FIELD_OK, equalTo(true));
    }

    @Test
    @DisplayName("Создание курьера с существующим логином")
    public void createCourierWithExistLogin() {
        Courier courier = GenerateCourier.getCourier();
        log.info(CREATE_COURIER, courier.getLogin());
        Response response = RequestApi.post(courier, ROOT);
        log.info(RESPONSE, response.body().asString());
        log.info("Повторное создание курьера с логином {}", courier.getLogin());
        Response conflictResponse = RequestApi.post(courier, ROOT);
        log.info(RESPONSE + "\n", conflictResponse.body().asString());
        conflictResponse.then().statusCode(409).and().body(FIELD_MESSAGE, equalTo(MESSAGE_EXISTING_LOGIN));
    }

    @Test
    @DisplayName("Создание курьера без поля firstName")
    public void createCourierWithoutName(){
        getResponse("firstName", "").then().statusCode(201).and().body(FIELD_OK,equalTo(true));
    }
    @Test
    @DisplayName("Создание курьера без поля firstName")
    public void createCourierWithoutNameNull(){
        getResponse("firstName", null).then().statusCode(201).and().body(FIELD_OK,equalTo(true));
    }

    @Test
    @DisplayName("Создание курьера без поля password")
    public void createCourierWithoutPassword() {
        getResponse("password", "").then().statusCode(400).and().body(FIELD_MESSAGE, equalTo(MESSAGE_WITHOUT_REQUIRED_FIELDS));
    }

    @Test
    @DisplayName("Создание курьера password = null")
    public void createCourierWithPasswordNull() {
        getResponse("password", null).then().statusCode(400).and().assertThat().body(FIELD_MESSAGE, equalTo(MESSAGE_WITHOUT_REQUIRED_FIELDS));
    }

    @Test
    @DisplayName("Создание курьера без поля login")
    public void createCourierWithoutLogin() {
        getResponse("login", "").then().statusCode(400).and().assertThat().body(FIELD_MESSAGE, equalTo(MESSAGE_WITHOUT_REQUIRED_FIELDS));
    }

    @Test
    @DisplayName("Создание курьера login = null")
    public void createCourierWithLoginNull() {
        getResponse("login", null).then().statusCode(400).and().assertThat().body(FIELD_MESSAGE, equalTo(MESSAGE_WITHOUT_REQUIRED_FIELDS));
    }

    private Response getResponse(String key, String data) {
        Courier courier = GenerateCourier.getCourier();
        if(Objects.equals(key, "login")){
            courier.setLogin(data);
        } else if (Objects.equals(key, "password")) {
            courier.setPassword(data);
        } else if (Objects.equals(key, "firstName")) {
            courier.setFirstname(data);
        }

        log.info(CREATE_COURIER, courier);
        Response response = RequestApi.post(courier, ROOT);
        log.info(RESPONSE, response.body().asString());
        return response;
    }
}
