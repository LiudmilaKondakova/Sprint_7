package recource.com.example.courier;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;

import java.util.Objects;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;

@Slf4j
public class RequestApi {

    @Before
    public void setUp() {
        BasePage.installSpec(BasePage.requestSpec());
    }


    @Step("Post запрос")
    public static Response post(Object courier, String endPoint){
        BasePage.installSpec(BasePage.requestSpec());
        return given()
                .body(courier)
                .when()
                .post(endPoint);
    }

    @Step("Put запрос")
    public static Response put(String endPoint) {
        BasePage.installSpec(BasePage.requestSpec());
        return given()
                .put(endPoint);
    }
    @Step("Delete запрос")
    public static Response delete(String endPoint){
        BasePage.installSpec(BasePage.requestSpec());
        return given()
                .delete(endPoint);
    }

    @Step("Get запрос")
    public static Response get(String endPoint) {
        BasePage.installSpec(BasePage.requestSpec());
        return given()
                .get(endPoint);
    }
}
