package createCourier;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class BasePage {
    public static final String BASE_URI = "https://qa-scooter.praktikum-services.ru/";
    public static final String BASE_PATH = "/api/v1";

    public static RequestSpecification spec(){
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(BASE_URI)
                .setBasePath(BASE_PATH)
                .build();
    }
}
