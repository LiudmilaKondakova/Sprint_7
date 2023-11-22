package createCourier;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;

public class CreateCourier extends BasePage{
    private final static String ROOT = "/courier";
    private final static String LOGIN = "/login";



    @Step("Создание курьера")
    public static Response create(Courier courier){
        BasePage.installSpec(BasePage.requestSpec());
        return given()
//                .spec()
                .body(courier)
                .when()
                .post(ROOT);
    }

    @Step("Создание курьера без заполнения поля password")
    public Response createWithoutPassword(CourierWithoutPassword courierWithoutPassword){
        return given()
//                .spec(requestSpecification)
                .body(courierWithoutPassword)
                .when()
                .post(ROOT);
    }

    @Step("Создание курьера без заполнения поля login")
    public Response createWithoutLogin(CourierWithoutLogin courierWithoutLogin){
        return given()
                .spec(requestSpecification)
                .body(courierWithoutLogin)
                .when()
                .post(ROOT);
    }

    @Step("Авторизация курьера")
    public Response login(CourierAuth courierAuth){
        return given()
                .spec(requestSpecification)
                .body(courierAuth)
                .when().post(ROOT + LOGIN);
    }

    @Step("Авторизация курьера без поля login")
    public Response loginWithoutLogin(CourierAuthWithoutLogin courierAuthWithoutLogin){
        return given()
                .spec(requestSpecification)
                .body(courierAuthWithoutLogin)
                .when().post(ROOT + LOGIN);
    }

    @Step("Авторизация курьера без поля password")
    public Response loginWithoutPassword(CourierAuthWithoutPassword courierAuthWithoutPassword){
        return given()
                .spec(requestSpecification)
                .body(courierAuthWithoutPassword)
                .when().post(ROOT + LOGIN);
    }

    @Step("Удаление курьера")
    public Response deleteCourier(Integer courierId){
        return given()
                .spec(requestSpecification)
                .delete(String.format("/%d", courierId), ROOT);
    }
}
