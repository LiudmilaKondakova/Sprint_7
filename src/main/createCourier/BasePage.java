package createCourier;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class BasePage {
    public static final String BASE_URI = "https://qa-scooter.praktikum-services.ru/api/v1";

    public static RequestSpecification requestSpec(){
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setContentType(ContentType.JSON)
                .build();
    }
    public static void installSpec(RequestSpecification request){
        RestAssured.requestSpecification = request;
    }
}
