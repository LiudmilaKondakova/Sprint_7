import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import recource.com.example.courier.Courier;
import recource.com.example.courier.CourierAuth;
import recource.com.example.courier.GenerateCourier;
import recource.com.example.courier.RequestApi;

import static org.hamcrest.CoreMatchers.equalTo;
import static recource.com.example.courier.MyComponent.*;

@Slf4j
public class DeleteCourierTest {

    @Test
    @DisplayName("Удаление курьера")
    public void deleteCourier() {
        Courier courier = GenerateCourier.getCourier();
        log.info(CREATE_COURIER, courier);
        Response response = RequestApi.post(courier, ROOT);
        CourierAuth courierAuth = new CourierAuth(courier);
        log.info(COURIER_AUTHORIZATION, courierAuth);
        Response responseAuth = RequestApi.post(courierAuth, ROOT + LOGIN);
        log.info(RESPONSE + "\n", responseAuth.body().asString());

        Integer id = responseAuth.body().path("id");
        Response responseDelete = RequestApi.delete(ROOT + "/" + id);
        log.info(RESPONSE, responseDelete.body().asString());

        responseDelete.then().assertThat().body(FIELD_OK, equalTo(true)).and().statusCode(200);
        log.info(DELETE + " прошло корректно", id);
    }

    @Test
    @DisplayName("Удаление курьера с несуществующим id")
    public void deleteNonExist() {
        Integer id = Integer.MAX_VALUE;
        log.info(DELETE, id);
        Response responseDelete = RequestApi.delete(ROOT + "/" + id);
        log.info(RESPONSE + "\n", responseDelete.body().asString());
        responseDelete.then().statusCode(404).and().assertThat().body(FIELD_MESSAGE, equalTo(MESSAGE_NOT_FOUND));
    }
}
