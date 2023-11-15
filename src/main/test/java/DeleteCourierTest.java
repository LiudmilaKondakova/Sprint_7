package test.java;

import createCourier.Courier;
import createCourier.CreateCourier;
import createCourier.GenerateCourier;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

@Slf4j
public class DeleteCourierTest {
    private final GenerateCourier generateCourier = new GenerateCourier();
    private final CreateCourier createCourier = new CreateCourier();
    private static final String FIELD_MESSAGE = "message";
    private static final String FIELD_OK = "ok";
    private static final String MESSAGE_NOT_FOUND = "урьера с таким id нет";
    private static final String RESPONSE = "Получен ответ от сервера: {}";
    private static final String DELETE = "Удаление курьера с id = {}";
    private Integer id = 0;

    @Test
    @DisplayName("Удаление курьера")
    public void deleteCourier() {
        Courier courier = generateCourier.getCourier();
        CreateCourier.create(courier);
        id = createCourier.login(generateCourier.getCourierAuth(courier)).body().path("id");
        log.info(DELETE, id);

        Response response;
        response = createCourier.deleteCourier(id);
        log.info(RESPONSE, response.body().asString());

        response.then().assertThat().body(FIELD_OK, equalTo(true)).and().statusCode(200);
        log.info(DELETE + "произошло корректно", id);
    }

    @Test
    @DisplayName("Удаление курьера с несуществующим id")
    public void deleteNonExist() {
        id = Integer.MIN_VALUE;
        log.info(DELETE, id);
        Response response = createCourier.deleteCourier(id);
        log.info(RESPONSE + "\n", response.body().asString());
        response.then().statusCode(404).and().assertThat().body(FIELD_MESSAGE, equalTo(MESSAGE_NOT_FOUND));
    }
}
