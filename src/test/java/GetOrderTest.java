import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Test;
import recource.com.example.courier.RequestApi;
import recource.com.example.order.Order;

import static org.hamcrest.CoreMatchers.equalTo;
import static recource.com.example.courier.MyComponent.*;


@Slf4j
public class GetOrderTest {

    private final String[] color = new String[] {"black"};
    private Integer trackId;

    @After
    public void delete() {
        if (trackId != null && trackId > 0) {
            RequestApi.put("/orders/cancel" + "/" + trackId);
            log.info("Удален заказ: {}", trackId);
        }
    }

    @Test
    @DisplayName("Получение заказа по номеру")
    public void getOrderNumber() {
        Response response = RequestApi.get(ORDERS + "/track?t=" + createOrder().body().path(FIELD_TRACK));
        log.info(RESPONSE, response.body().asString());
        Order responseOrder = response.body().jsonPath().getObject("order", Order.class);
        log.info("Создан объект: {}\n", responseOrder);
        response.then().statusCode(200);
    }

    @Test
    @DisplayName("Получение заказа по несуществующему номеру")
    public void getOrderNonExistNumber() {
        Response response = RequestApi.get(ORDERS + "/track?t=" + Integer.MAX_VALUE);
        log.info(RESPONSE, response.body().asString());
        response.then().statusCode(404).and().body(FIELD_MESSAGE, equalTo(MESSAGE_ORDER_NOT_FOUND));
    }

    @Test
    @DisplayName("Получение заказа без номера")
    public void getOrderWithoutNumber() {
        Response response = RequestApi.get(ORDERS + "/track?t=");
        log.info(RESPONSE, response.body().asString());
        response.then().statusCode(400).and().body(FIELD_MESSAGE, equalTo(MESSAGE_NOT_ENOUGH_DATA));
    }

    private Response createOrder() {
        String firstName = "Людмила";
        String lastName = "Кондакова";
        String address = "Чайковского 18";
        String metroStation = "Бульвар Рокоссовского";
        String phone = "79817776666";
        Integer rentTime = 1;
        String deliveryTime = "2023-11-15";
        String comment = "comment";
        Order order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryTime, comment, color);
        Response response = RequestApi.post(order, ORDERS);
        return response;
    }
}
