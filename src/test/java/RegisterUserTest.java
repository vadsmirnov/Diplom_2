import client.ClientUser;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import junitparams.naming.TestCaseName;
import mod.RegistUser;
import org.junit.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import utils.GenerationUser;

import java.util.List;

import static org.apache.http.HttpStatus.*;
import static utils.GenerationUser.UserField.*;

@RunWith(JUnitParamsRunner.class)
public class RegisterUserTest {

    private RegistUser registerData;
    private String token = "";
    private int statusCode;
    private boolean isRegistered;

    @Test
    @DisplayName("Создание пользователя с корректными данными")
    public void registerUserCorrectdData() {
        registerData = GenerationUser.getDefaultRegistrData();
        ValidatableResponse responseRegister = ClientUser.registerUser(registerData);

        token = responseRegister.extract().path("accessToken");
        statusCode = responseRegister.extract().statusCode();
        isRegistered = responseRegister.extract().path("success");


        Assert.assertEquals(List.of(SC_OK, true),
                List.of(statusCode, isRegistered));
    }

    @Test
    @DisplayName("Создание пользователя который уже зарегистрирован")
    public void registerUserDouble() {
        registerData = GenerationUser.getDefaultRegistrData();
        ValidatableResponse responseRegisterOne = ClientUser.registerUser(registerData);
        ValidatableResponse responseRegisterTwo = ClientUser.registerUser(registerData);

        token = responseRegisterOne.extract().path("accessToken");
        statusCode = responseRegisterTwo.extract().statusCode();
        isRegistered = responseRegisterTwo.extract().path("success");


        Assert.assertEquals(List.of(SC_FORBIDDEN, false),
                List.of(statusCode, isRegistered));
    }

    @Test
    @Parameters(method = "registrUserWithEmptyField")
    @TestCaseName("Создание пользователя без {0}")
    public void registrUserWithEmptyField(GenerationUser.UserField emptyField) {
        registerData = GenerationUser.getRegistrDataWithOneEmptyField(emptyField);
        ValidatableResponse responseRegister = ClientUser.registerUser(registerData);

        statusCode = responseRegister.extract().statusCode();
        isRegistered = responseRegister.extract().path("success");

        Assert.assertEquals(List.of(SC_FORBIDDEN, false),
                List.of(statusCode, isRegistered));
    }

    private Object[][] registrUserWithEmptyField() {
        return new Object[][]{
                {EMAIL}, {PASSWORD}, {NAME},
        };
    }

    @After
    public void tearDown(){
        if (token != null){
            ClientUser.deleteUser(token);
        }
    }
}
