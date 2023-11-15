package order;

import createCourier.BasePage;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class OrderSteps extends BasePage {
    private final static String ROOT = "/orders";
    private static final String CANCEL = "/cancel";
    private static final String TRACK = "/track";

    @Step("Создание заказа")
    public Response createOrder(Order order) {
        return spec()
                .body(order)
                .when()
                .post(ROOT);
    }

    @Step("Завершение заказа")
    public Response cancelOrder(Integer id) {
        return spec()
                .queryParam("track", id)
                .put(ROOT + CANCEL);
    }

    @Step("Получение списка заказов")
    public Response getOrder() {
        return spec()
                .get(ROOT);
    }

    @Step("Получение заказа по номеру")
    public Response getOrderByNumber(Integer trackId) {
        return spec()
                .queryParam("t", trackId)
                .get(ROOT + TRACK);
    }

    @Step("Получение заказа без номера")
    public Response getOrderWithoutNumber() {
        return spec()
                .get(ROOT + TRACK);
    }
}
