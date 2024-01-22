import client.ClientUser;
import client.OrderClien;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import junitparams.JUnitParamsRunner;
import mod.CreateOrder;
import mod.RegistUser;
import org.junit.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import utils.GenerationOrder;
import utils.GenerationUser;

import java.util.List;

import static org.apache.http.HttpStatus.*;

@RunWith(JUnitParamsRunner.class)
public class GetUserOrdersTest {

    private final static RegistUser registerData = GenerationUser.getDefaultRegistrData();
    private final static CreateOrder createOrderData = GenerationOrder.getDefaultOrder();
    private static String token = "";
    private int statusCode;
    private boolean isGot;

    @BeforeClass
    public static void setUp() {
        ValidatableResponse responseRegister = ClientUser.registerUser(registerData);
        token = responseRegister.extract().path("accessToken");
        ValidatableResponse responseCreateOrder = OrderClien.createOrder(createOrderData, token);
    }

    @AfterClass
    public static void tearDown() {
        ValidatableResponse responseDelete = ClientUser.deleteUser(token);
    }

    @Test
    @DisplayName("Получение заказов авторизованного пользователя")
    public void getAuthorizedUserOrders() {
        ValidatableResponse responseGetOrders = OrderClien.getUserOrders(token);
        statusCode = responseGetOrders.extract().statusCode();
        isGot = responseGetOrders.extract().path("success");
        List<Object> orders = responseGetOrders.extract().path("orders");

        Assert.assertEquals("Ошибка в коде или теле ответа", List.of(SC_OK, true, false),
                List.of(statusCode, isGot, orders.isEmpty()));
    }

    @Test
    @DisplayName("Получение заказов неавторизованного пользователя")
    public void getUnauthorizedUserOrders() {
        ValidatableResponse responseGetOrders = OrderClien.getUserOrders("qwe");
        statusCode = responseGetOrders.extract().statusCode();
        isGot = responseGetOrders.extract().path("success");

        Assert.assertEquals(List.of(SC_UNAUTHORIZED, false),
                List.of(statusCode, isGot));
    }
}