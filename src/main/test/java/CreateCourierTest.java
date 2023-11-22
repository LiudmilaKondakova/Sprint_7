package test.java;

import createCourier.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

@Slf4j
public class CreateCourierTest extends BaseTest{
    private static final String FIELD_MESSAGE = "message";
    private static final String FIELD_OK = "ok";
    private static final String MESSAGE_EXISTING_LOGIN = "Этот логин уже используется. Попробуйте другой.";
    private static final String MESSAGE_WITHOUT_REQUIRED_FIELDS = "Недостаточно данных для создания учетной записи";
    private static final String RESPONSE = "Получен ответ от сервера: {}";
    private static final String CREATE_COURIER = "Создание курьера: {}";
    private final CreateCourier createCourier = new CreateCourier();

    @Test
    @DisplayName("Создание курьера со всеми параметрами")
    public void createCourier() {
        Courier courier = GenerateCourier.getCourier();
        log.info(CREATE_COURIER, courier.getLogin());
        Response response = CreateCourier.create(courier);
        log.info(RESPONSE + "\n", response.body().asString());
        response.then().assertThat().statusCode(201).and().body(FIELD_OK, equalTo(true));
    }

    @Test
    @DisplayName("Создание курьера с существующим логином")
    public void createCourierWithExistLogin() {
        Courier courier = GenerateCourier.getCourier();
        log.info(CREATE_COURIER, courier.getLogin());
        Response response = CreateCourier.create(courier);
        log.info(RESPONSE, response.body().asString());
        log.info("Повторное создание курьера с логином {}", courier.getLogin());
        Response conflictResponse = CreateCourier.create(courier);
        log.info(RESPONSE + "\n", conflictResponse.body().asString());
        conflictResponse.then().statusCode(409).and().body(FIELD_MESSAGE, equalTo(MESSAGE_EXISTING_LOGIN));
    }

    @Test
    @DisplayName("Создание курьера без поля firstName")
    public void createCourierWithoutName(){
        Courier courier = GenerateCourier.getCourier();
        courier.setFirstname("");
        log.info(CREATE_COURIER, courier);
        Response response = CreateCourier.create(courier);
        log.info(RESPONSE + "\n", response.body().asString());
        response.then().statusCode(201).and().body(FIELD_OK,equalTo(true));
    }

    @Test
    @DisplayName("Создание курьера без поля password")
    public void createCourierWithoutPassword() {
        Courier courier = GenerateCourier.getCourier();
        courier.setPassword("");
        log.info(CREATE_COURIER, courier);
        Response response = CreateCourier.create(courier);
        log.info(RESPONSE, response.body().asString());
        response.then().statusCode(400).and().body(FIELD_MESSAGE, equalTo(MESSAGE_WITHOUT_REQUIRED_FIELDS));
    }

    @Test
    @DisplayName("Создание курьера password = null")
    public void createCourierWithPasswordNull() {
        Courier courier = GenerateCourier.getCourier();
        courier.setPassword(null);
        log.info(CREATE_COURIER, courier);
        Response response = CreateCourier.create(courier);
        log.info(RESPONSE, response.body().asString());
        response.then().statusCode(400).and().assertThat().body(FIELD_MESSAGE, equalTo(MESSAGE_WITHOUT_REQUIRED_FIELDS));
    }

    @Test
    @DisplayName("Создание курьера без поля login")
    public void createCourierWithoutLogin() {
        Courier courier = GenerateCourier.getCourier();
        courier.setLogin("");
        log.info(CREATE_COURIER, courier);
        Response response = CreateCourier.create(courier);
        log.info(RESPONSE, response.body().asString());
        response.then().statusCode(400).and().assertThat().body(FIELD_MESSAGE, equalTo(MESSAGE_WITHOUT_REQUIRED_FIELDS));
    }

    @Test
    @DisplayName("Создание курьера login = null")
    public void createCourierWithLoginNull() {
        Courier courier = GenerateCourier.getCourier();
        courier.setLogin(null);
        log.info(CREATE_COURIER, courier);
        Response response = CreateCourier.create(courier);
        log.info(RESPONSE, response.body().asString());
        response.then().statusCode(400).and().assertThat().body(FIELD_MESSAGE, equalTo(MESSAGE_WITHOUT_REQUIRED_FIELDS));
    }
}
