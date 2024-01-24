package client;

import mod.CreateOrder;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClien extends Client {
    private final static String ORDERS = "api/orders";

    @Step("Создание заказа")
    public static ValidatableResponse createOrder(CreateOrder data, String bearerPlusToken) {
        return given()
                .spec(getSpec(bearerPlusToken))
                .body(data)
                .log().all()
                .when()
                .post(ORDERS)
                .then()
                .log().all();
    }


    @Step("Заказ пользователя")
    public static ValidatableResponse getUserOrders(String bearerPlusToken) {
        return given()
                .spec(getSpec(bearerPlusToken))
                .log().all()
                .when()
                .get(ORDERS)
                .then()
                .log().all();
    }

}
