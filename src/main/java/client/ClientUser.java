package client;

import mod.AuthorizationUser;
import mod.RegistUser;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class ClientUser extends Client {

        private final static String REGISTER_USER = "api/auth/register";
        private final static String LOGIN_USER = "api/auth/login";
        private final static String UPDATE_USER = "api/auth/user";

        @Step("Регистрация пользователя")
        public static ValidatableResponse registerUser(RegistUser data) {
            return given()
                    .spec(getSpec())
                    .body(data)
                    .log().all()
                    .when()
                    .post(REGISTER_USER)
                    .then()
                    .log().all();
        }

        @Step("Логин пользователя")
        public static ValidatableResponse loginUser(AuthorizationUser data) {
            return given()
                    .spec(getSpec())
                    .body(data)
                    .log().all()
                    .when()
                    .post(LOGIN_USER)
                    .then()
                    .log().all();
        }

        @Step("Редактирвоание пользователя")
        public static  ValidatableResponse updateUser(RegistUser data, String bearerPlusToken) {
            return given()
                    .spec(getSpec(bearerPlusToken))
                    .body(data)
                    .log().all()
                    .when()
                    .patch(UPDATE_USER)
                    .then()
                    .log().all();
        }

        @Step("Удаление пользователя")
        public static ValidatableResponse deleteUser(String bearerPlusToken) {
            return given()
                    .spec(getSpec(bearerPlusToken))
                    .when()
                    .log().all()
                    .delete(UPDATE_USER)
                    .then()
                    .log().all();
        }

    }