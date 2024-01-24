import client.ClientUser;
import client.OrderClien;
import io.restassured.response.ValidatableResponse;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import junitparams.naming.TestCaseName;
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
public class CreateOrderTest {

    private final static RegistUser registerData = GenerationUser.getDefaultRegistrData();
    private static String token = "";
    private int statusCode;
    private boolean isCreated;
    CreateOrder createOrderData;

    @BeforeClass
    public static void setUp(){
        ValidatableResponse responseRegister = ClientUser.registerUser(registerData);
        token = responseRegister.extract().path("accessToken");
    }

    @After
    public void tearDown(){
        if (token != null){
            ClientUser.deleteUser(token);
        }
    }

    @Test
    @Parameters(method = "createOrderParameters")
    @TestCaseName("Создание заказа с разными параметрами")
    public void createOrder(boolean isAuth, boolean haveIngredients, boolean isHashCorrect, int status) {
        String tokenInit = "qwe";
        if (isAuth) {tokenInit = token;}
        if (isHashCorrect) {
            if (haveIngredients) {
                createOrderData = GenerationOrder.getDefaultOrder();
            } else {
                createOrderData = GenerationOrder.getOrderWithoutIngredients();
            }
        } else {
            createOrderData = GenerationOrder.getOrderWithInvalidHash();
        }
        ValidatableResponse responseCreateOrder = OrderClien.createOrder(createOrderData, tokenInit);

        statusCode = responseCreateOrder.extract().statusCode();
        if (isHashCorrect) {
            isCreated = responseCreateOrder.extract().path("success");
        } else {isCreated = false;}

        Assert.assertEquals(
                List.of(status, haveIngredients & isHashCorrect),
                List.of(statusCode, isCreated));
    }

    private Object[][] createOrderParameters() {
        return new Object[][]{
                {true, true, true, SC_OK},
                {false, true, true, SC_OK},
                {true, false, true, SC_BAD_REQUEST},

        };
    }
}