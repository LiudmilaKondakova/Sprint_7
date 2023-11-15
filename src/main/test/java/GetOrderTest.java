package test.java;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import order.GenerateOrder;
import order.Order;
import order.OrderSteps;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;


@Slf4j
public class GetOrderTest {
    private static final String RESPONSE = "Получен ответ от сервера: {}";
    private static final String FIELD_MESSAGE = "message";
    private static final String MESSAGE_ORDER_NOT_FOUND = "Заказ не найден";
    private static final String MESSAGE_NOT_ENOUGH_DATA = "Недостаточно данных для поиска";
    private static final String FIELD_TRACK = "track";
    private final OrderSteps orderSteps = new OrderSteps();
    private final GenerateOrder generateOrder = new GenerateOrder();
    private final String[] color = new String[] {"black"};
    private final Utils utils = new Utils();
    private Order order;
    private Integer trackId;

    @After
    public void delete() {
        if (trackId != null && trackId > 0) {
            utils.cancelOrder(trackId);
        }
    }

    @Test
    @DisplayName("Получение заказа по номеру")
    public void getOrderNumber() {
        createOrder();
        Response response = orderSteps.getOrderByNumber(trackId);
        log.info(RESPONSE, response.body().asString());
        Order responseOrder = response.body().jsonPath().getObject("order", Order.class);
        log.info("Создан объект: {}\n", responseOrder);
        assertEquals("Полученный заказ не соответствует", order, responseOrder);
    }

    @Test
    @DisplayName("Получение заказа по несуществующему номеру")
    public void getOrderNonExistNumber() {
        Integer failTrackId = Integer.MAX_VALUE;
        Response response = orderSteps.getOrderByNumber(failTrackId);
        log.info(RESPONSE, response.body().asString());
        response.then().statusCode(404).and().body(FIELD_MESSAGE, equalTo(MESSAGE_ORDER_NOT_FOUND));
    }

    @Test
    @DisplayName("Получение заказа без номера")
    public void getOrderWithoutNumber() {
        Response response = orderSteps.getOrderWithoutNumber();
        log.info(RESPONSE, response.body().asString());
        response.then().statusCode(400).and().body(FIELD_MESSAGE, equalTo(MESSAGE_NOT_ENOUGH_DATA));
    }

    private void createOrder() {
        String firstName = "Людмила";
        String lastName = "Кондакова";
        String address = "Чайковского 18";
        String metroStation = "Бульвар Рокоссовского";
        String phone = "79817776666";
        Integer rentTime = 1;
        String deliveryTime = "15.11.2023";
        String comment = "comment";
        order = (Order) generateOrder.getOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryTime, comment, color);
        Response response = orderSteps.createOrder(order);
        trackId = response.body().path(FIELD_TRACK);
        log.info("Номер заказа: {}", trackId);
    }
}
