package test.java;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import order.GenerateOrder;
import order.Order;
import order.OrderSteps;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static order.ScooterColor.COLOR_BLACK;
import static order.ScooterColor.COLOR_GREY;
import static org.hamcrest.Matchers.notNullValue;

@Slf4j
@RunWith(Parameterized.class)
public class CreateOrderTest {
    private static final String RESPONSE = "Получен ответ от сервера: {}";
    private static final String FIELD_TRACK = "track";
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final Integer rentTime;
    private final String deliveryDate;
    private final String comment;
    private final String[] color;
    private final OrderSteps orderSteps = new OrderSteps();
    private final GenerateOrder generateOrder = new GenerateOrder();
    private final Utils utils = new Utils();
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
                {"Людмила", "Кондакова", "Чайковского 18", "Бульвар Рокоссовского", "79817776666", "15.11.2023", 1, new String[]{COLOR_BLACK}, "comment"},
                {"Иван", "Иванов", "Чайковского 18", "Черкизовская", "79817776666", "16.11.2023", 2, new String[]{COLOR_GREY}, "comment"},
                {"Петр", "Петров", "Чайковского 18", "Преображенская площадь", "79817776666", "18.11.2023", 3, new String[]{COLOR_BLACK, COLOR_GREY}, "comment"},
                {"Людмила", "Кондакова", "Чайковского 18", "Бульвар Рокоссовского", "79817776666", "19.11.2023", 4, new String[]{}, "comment"},
                {"Людмила", "Кондакова", "Чайковского 18", "Бульвар Рокоссовского", "79817776666", "20.11.2023", 5, null, "comment"},
        };
    }

    @After
    public void delete() {
        if (trackId != null && trackId > 0) {
            utils.cancelOrder(trackId);
        }
    }

    @Test
    @DisplayName("Создание заказа")
    public void createOrder() {
        Order order = (Order) generateOrder.getOrder(firstName, lastName, address, metroStation, phone,rentTime,deliveryDate,comment,color);
        log.info("Создание заказа: {}", order);
        Response response = orderSteps.createOrder(order);
        log.info(RESPONSE, response.body().asString());
        trackId = response.body().path(FIELD_TRACK);
        log.info("Создан заказ №: {}\n", trackId);
        response.then().statusCode(201).and().assertThat().body(FIELD_TRACK, notNullValue());
    }
}
