import client.ClientUser;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import junitparams.JUnitParamsRunner;
import mod.AuthorizationUser;
import mod.RegistUser;
import org.junit.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import utils.GenerationUser;

import java.util.List;

import static org.apache.http.HttpStatus.*;

@RunWith(JUnitParamsRunner.class)
public class AuthorizationUserTest {

    private final RegistUser registerData = GenerationUser.getDefaultRegistrData();
    private AuthorizationUser loginData;
    private String token = "";
    private int statusCode;
    private boolean isLoggedIn;

    @Before
    public void setUp(){
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
    @DisplayName("пользователь с корректными данными")
    public void loginCorrectedData() {
        loginData = GenerationUser.getDefaultLoginData();
        ValidatableResponse responseLogin = ClientUser.loginUser(loginData);

        statusCode = responseLogin.extract().statusCode();
        isLoggedIn = responseLogin.extract().path("success");

        Assert.assertEquals(List.of(SC_OK, true),
                List.of(statusCode, isLoggedIn));
    }

    @Test
    @DisplayName("Пользователь с неверным логином и паролем")
    public void loginUncorrectedUser() {
        loginData = GenerationUser.getUncorrectedLoginData();
        ValidatableResponse responseLogin = ClientUser.loginUser(loginData);

        statusCode = responseLogin.extract().statusCode();
        isLoggedIn = responseLogin.extract().path("success");

        Assert.assertEquals(List.of(SC_UNAUTHORIZED, false),
                List.of(statusCode, isLoggedIn));
    }
}