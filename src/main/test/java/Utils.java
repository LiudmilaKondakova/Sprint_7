package test.java;

import createCourier.Courier;
import createCourier.CourierAuth;
import createCourier.CreateCourier;
import createCourier.GenerateCourier;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import order.OrderSteps;

import static org.hamcrest.Matchers.equalTo;

/** @noinspection PlaceholderCountMatchesArgumentCount*/
@Slf4j
public class Utils {
    public static final String FIELD_ID = "id";
    public static final String FIELD_OK = "ok";
    public static final String RESPONSE = "Получен ответ от сервера: {}";
    private final CreateCourier createCourier = new CreateCourier();
    private final OrderSteps orderSteps = new OrderSteps();
    private final GenerateCourier generateCourier = new GenerateCourier();

    public Integer getCourierId(Courier courier) {
        CourierAuth courierAuth = generateCourier.getCourierAuth(courier);
        log.info("Авторизация курьера с паролем на сервере", courierAuth.getLogin(), courierAuth.getPassword());

        Response response = createCourier.login(courierAuth);
        log.info(RESPONSE, response.body().asString());
        Integer courierId = response.body().path(FIELD_ID);
        log.info("Курьер с id = {} авторизован", courierId);
        return courierId;
    }

    public void deleteCourier(Courier courier) {
        int courierId = getCourierId(courier);
        log.info("Удаление созданного курьера по id", courierId);
        Response response = createCourier.deleteCourier(courierId);
        log.info(RESPONSE, response.body().asString());

        response.then().assertThat().body(FIELD_OK, equalTo(true)).and().statusCode(200);
        log.info("Курьер с id удален", courierId);
    }

    public void cancelOrder(Integer id) {
        Response response = orderSteps.cancelOrder(id);
        response.then().assertThat().body(FIELD_OK, equalTo(true)).and().statusCode(200);
        log.info("Заказ завершен", id);
    }
}
