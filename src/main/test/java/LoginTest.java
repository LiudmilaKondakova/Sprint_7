package test.java;

import createCourier.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThan;

@Slf4j
public class LoginTest extends BaseTest{
    private static final String FIELD_MESSAGE = "message";
    private static final String FIELD_ID = "id";
    private static final String RESPONSE = "Получен ответ от сервера: {}";
    private static final String MESSAGE_NO_ENOUGH_DATA = "Недостаточно данных для входа";
    private static final String COURIER_AUTHORIZATION = "Авторизация курьера: {}";
    private static final String MESSAGE_COURIER_NOT_FOUND = "Учетная запись не найдена";
    private final GenerateCourier generateCourier = new GenerateCourier();
    private final CreateCourier createCourier = new CreateCourier();
    private CourierAuth courierAuth;

    @Before
    public void setUp() {
        courier = generateCourier.getCourier();
        CreateCourier.create(courier);
        courierAuth = generateCourier.getCourierAuth(courier);
    }

    @Test
    @DisplayName("Авторизация")
    public void courierLogin() {
        log.info(COURIER_AUTHORIZATION, courierAuth);
        Response response = createCourier.login(courierAuth);
        log.info(RESPONSE + "\n", response.body().asString());
        response.then().statusCode(HttpStatus.SC_OK).and().assertThat().body(FIELD_ID, allOf(notNullValue(), greaterThan(0)));
    }

    @Test
    @DisplayName("Авторизация без поля login")
    public void courierWithoutLogin() {
        CourierAuthWithoutLogin courierAuthWithoutLogin = generateCourier.getCourierAuthWithoutLogin(courier);
        log.info(COURIER_AUTHORIZATION, courierAuthWithoutLogin);
        Response response = createCourier.loginWithoutLogin(courierAuthWithoutLogin);
        log.info(RESPONSE + "\n", response.body().asString());
        response.then().statusCode(HttpStatus.SC_BAD_REQUEST).and().body(FIELD_MESSAGE, equalTo(MESSAGE_NO_ENOUGH_DATA));
    }

    @Test
    @DisplayName("Авторизация без поля password")
    public void courierWithoutPassword() {
        CourierAuthWithoutPassword courierAuthWithoutPassword = generateCourier.getCourierAuthWithoutPassword(courier);
        log.info(COURIER_AUTHORIZATION, courierAuthWithoutPassword);
        Response response = createCourier.loginWithoutPassword(courierAuthWithoutPassword);
        log.info(RESPONSE + "\n", response.body().asString());
        response.then().statusCode(HttpStatus.SC_BAD_REQUEST).and().body(FIELD_MESSAGE, Matchers.equalTo(MESSAGE_NO_ENOUGH_DATA));
    }

    @Test
    @DisplayName("Авторизация login = null")
    public void courierWithLoginNull() {
        CourierAuth courierAuthWithLoginNull = generateCourier.getCourierAuthWithLoginNull(courier);
        log.info(COURIER_AUTHORIZATION, courierAuthWithLoginNull);
        Response response = createCourier.login(courierAuthWithLoginNull);
        log.info(RESPONSE + "\n", response.body().asString());
        response.then().statusCode(HttpStatus.SC_BAD_REQUEST).and().body(FIELD_MESSAGE, Matchers.equalTo(MESSAGE_NO_ENOUGH_DATA));
    }

    @Test
    @DisplayName("Авторизация password = null")
    public void courierWithPasswordNull() {
        CourierAuth courierAuthWithPasswordNull = generateCourier.getCourierAuthWithPasswordNull(courier);
        log.info(COURIER_AUTHORIZATION, courierAuthWithPasswordNull);
        Response response = createCourier.login(courierAuthWithPasswordNull);
        log.info(RESPONSE + "\n", response.body().asString());
        response.then().statusCode(HttpStatus.SC_BAD_REQUEST).and().body(FIELD_MESSAGE, Matchers.equalTo(MESSAGE_NO_ENOUGH_DATA));
    }

    @Test
    @DisplayName("Авторизация с неверным login и password")
    public void courierNoExist() {
        Courier courierNotCreate;
        courierNotCreate = generateCourier.getCourier();
        CourierAuth courierAuthNotCreate = generateCourier.getCourierAuth(courierNotCreate);
        log.info(COURIER_AUTHORIZATION, courierNotCreate);
        Response response = createCourier.login(courierAuthNotCreate);
        log.info(RESPONSE + "\n", response.body().asString());
        response.then().statusCode(HttpStatus.SC_NOT_FOUND).and().body(FIELD_MESSAGE, equalTo(MESSAGE_COURIER_NOT_FOUND));
    }
}
