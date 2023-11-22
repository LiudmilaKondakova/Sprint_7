import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import recource.com.example.courier.Courier;
import recource.com.example.courier.CourierAuth;
import recource.com.example.courier.GenerateCourier;
import recource.com.example.courier.RequestApi;
import recource.com.example.order.Order;

import static org.hamcrest.Matchers.notNullValue;
import static recource.com.example.courier.MyComponent.*;

@Slf4j
public class OrderTest {


    @Test
    @DisplayName("Получение списка заказа")
    public void getOrders() {
        Courier courier = GenerateCourier.getCourier();
        Response response = RequestApi.post(courier, ROOT);
        log.info(RESPONSE, response.body().asString());
        CourierAuth courierAuth = new CourierAuth(courier);
        Response responseAuth = RequestApi.post(courierAuth, ROOT + LOGIN);
        log.info(RESPONSE + "\n", responseAuth.body().asString());
        Response responseOrder = createOrder();
        log.info(RESPONSE, responseOrder.body().asString());
        Response responseGetOrder = RequestApi.put(ORDERS + ACCEPT + "/" + responseOrder.path(FIELD_TRACK) + "?courierId=" + responseAuth.path("id"));
        log.info(ORDERS + ACCEPT + "/" + responseOrder.path(FIELD_TRACK) + "?courierId=" + responseAuth.path("id"));
        log.info(RESPONSE, responseGetOrder.body().asString());
        Response responseOrders = RequestApi.get(ORDERS + "?courierId=" + responseAuth.path("id"));

        log.info(RESPONSE, responseOrders.body().asString());
        responseOrders.then().statusCode(200).and().assertThat().body(FIELDS_ORDER, notNullValue());
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
        String[] color = new String[] {"black"};
        Order order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryTime, comment, color);
        Response response = RequestApi.post(order, ORDERS);
        log.info(RESPONSE, response.body().asString());
        return response;
    }
}
