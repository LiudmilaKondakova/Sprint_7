import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import recource.com.example.courier.RequestApi;
import recource.com.example.order.Order;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static recource.com.example.courier.MyComponent.*;
import static recource.com.example.order.ScooterColor.COLOR_BLACK;
import static recource.com.example.order.ScooterColor.COLOR_GREY;
import static org.hamcrest.Matchers.notNullValue;

@Slf4j
@RunWith(Parameterized.class)
public class CreateOrderTest {

    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final Integer rentTime;
    private final String deliveryDate;
    private final String comment;
    private final String[] color;
    private Integer trackId;

    public CreateOrderTest(String firstName, String lastName, String address, String metroStation, String phone, Integer rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }


    @Parameterized.Parameters(name = "{index} : color {7}")
    public static Object[][] getParameters() {
        return new Object[][] {
                {"Людмила", "Кондакова", "Чайковского 18", "Бульвар Рокоссовского", "79817776666", 1, "2023-11-15", "comment",  new String[]{COLOR_BLACK}},
                {"Иван", "Иванов", "Чайковского 18", "Черкизовская", "79817776666", 2,"2023-11-16", "comment", new String[]{COLOR_GREY}},
                {"Петр", "Петров", "Чайковского 18", "Преображенская площадь", "79817776666", 3,"2023-11-17", "comment", new String[]{COLOR_BLACK, COLOR_GREY}},
                {"Людмила", "Кондакова", "Чайковского 18", "Бульвар Рокоссовского", "79817776666", 4,"2023-11-18",  "comment",new String[]{}},
                {"Людмила", "Кондакова", "Чайковского 18", "Бульвар Рокоссовского", "79817776666",5, "2023-11-19", "comment", null},
        };
    }

    @After
    public void delete() {
        if (trackId != null && trackId > 0) {
            RequestApi.put("/orders/cancel" + "/" + trackId);
            log.info("Удален заказ: {}", trackId);
        }
    }

    @Test
    @DisplayName("Создание заказа")
    public void createOrder() {
        Order order = new Order(firstName, lastName, address, metroStation, phone,rentTime,deliveryDate,comment,color);
        log.info("Создание заказа: {}", order);
        Response response = RequestApi.post(order, ORDERS);
        log.info(RESPONSE, response.body().asString());
        trackId = response.body().path(FIELD_TRACK);
        log.info("Создан заказ №: {}\n", trackId);
        response.then().statusCode(201).and().assertThat().body(FIELD_TRACK, notNullValue());
    }
}
