package test.java;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import order.OrderSteps;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;

@Slf4j
public class OrderTest {
    private static final String RESPONSE = "Получен ответ от сервера: {}";
    private static final String FIELDS_ORDER = "orders";
    final OrderSteps orderSteps = new OrderSteps();

    @Test
    @DisplayName("Получение списка заказа")
    public void getOrders() {
        Response response = orderSteps.getOrder();
        log.info(RESPONSE, response.body().asString());
        response.then().statusCode(200).and().assertThat().body(FIELDS_ORDER, notNullValue());
    }
}
