import client.ClientUser;
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
@RunWith(JUnitParamsRunner.class)
public class UpdateUserTest {

    private final RegistUser registerData = GenerationUser.getDefaultRegistrData();

    private final RegistUser updateData = GenerationUser.getDefaultUpdateData();
    private String token = "";
    private int statusCode;
    private boolean isUpdated;

    @Before
    public void setUp(){
        ValidatableResponse responseRegister = ClientUser.registerUser(registerData);
        token = responseRegister.extract().path("accessToken");
    }

    @After
    public void tearDown(){
        ValidatableResponse responseDelete = ClientUser.deleteUser(token);
    }

    @Test
    @Parameters(method = "updateUserWithAuthorization")
    @TestCaseName("редактирование пользователя с авторизацией: {0}")
    public void updateUserWithAuthorization(boolean isAuth, int status) {
        String tokenInit = "йцу";
        if (isAuth) {tokenInit = token;}
        ValidatableResponse responseUpdate = ClientUser.updateUser(updateData, tokenInit);

        statusCode = responseUpdate.extract().statusCode();
        isUpdated = responseUpdate.extract().path("success");

        Assert.assertEquals(List.of(status, isAuth),
                List.of(statusCode, isUpdated));
    }

    private Object[][] updateUserWithAuthorization() {
        return new Object[][]{
                {true, SC_OK},
                {false, SC_UNAUTHORIZED},
        };
    }
}
