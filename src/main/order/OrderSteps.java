package order;

import createCourier.BasePage;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class OrderSteps extends BasePage {
    private final static String ROOT = "/orders";
    private static final String CANCEL = "/cancel";
    private static final String TRACK = "/track";
    static RequestSpecification requestSpec = BasePage.requestSpec();
    @Step("Создание заказа")
    public Response createOrder(Order order) {
        return requestSpec()
                .body(order)
                .when()
                .post(ROOT);
    }

    @Step("Завершение заказа")
    public Response cancelOrder(Integer id) {
        return requestSpec()
                .queryParam("track", id)
                .put(ROOT + CANCEL);
    }

    @Step("Получение списка заказов")
    public Response getOrder() {
        return requestSpec()
                .get(ROOT);
    }

    @Step("Получение заказа по номеру")
    public Response getOrderByNumber(Integer trackId) {
        return requestSpec()
                .queryParam("t", trackId)
                .get(ROOT + TRACK);
    }

    @Step("Получение заказа без номера")
    public Response getOrderWithoutNumber() {
        return requestSpec()
                .get(ROOT + TRACK);
    }
}
