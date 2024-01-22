package utils;

import mod.RegistUser;
import mod.AuthorizationUser;


import static utils.GenerationDate.randomString;

public class GenerationUser {
    final static String EMAIL_DEFAULT = randomString(8) + "@" + randomString(4) + ".ru";
    final static String PASSWORD_DEFAULT = randomString(10);
    final static String NAME_DEFAULT = randomString(6);
    final static String EMAIL_UNCORRECTED = randomString(8) + "@" + randomString(4) + "." + randomString(3);
    final static String EMAIL_NEW = randomString(8) + EMAIL_DEFAULT;
    final static String PASSWORD_NEW = randomString(8);
    final static String NAME_NEW = randomString(6);

    public enum UserField {
        EMAIL,
        PASSWORD,
        NAME
    }

    public static RegistUser getDefaultRegistrData() {
        return new RegistUser(EMAIL_DEFAULT, PASSWORD_DEFAULT, NAME_DEFAULT);
    }

    public static RegistUser getRegistrDataWithOneEmptyField(UserField emptyField) {
        RegistUser data = null;
        switch (emptyField) {
            case EMAIL:
                data = new RegistUser("", PASSWORD_DEFAULT, NAME_DEFAULT);
                break;
            case PASSWORD:
                data = new RegistUser(EMAIL_DEFAULT, "", NAME_DEFAULT);
                break;
            case NAME:
                data = new RegistUser(EMAIL_DEFAULT, PASSWORD_DEFAULT, "");
                break;
        }
        return data;
    }

    public static AuthorizationUser getDefaultLoginData() {
        return new AuthorizationUser(EMAIL_DEFAULT, PASSWORD_DEFAULT);
    }

//    public static LoginUser getNewLoginData() {
//        return new LoginUser(EMAIL_NEW, PASSWORD_DEFAULT);
//    }

    public static AuthorizationUser getUncorrectedLoginData() {
        return new AuthorizationUser(EMAIL_UNCORRECTED, PASSWORD_DEFAULT);
    }

    public static RegistUser getDefaultUpdateData() {
        return new RegistUser(EMAIL_NEW, PASSWORD_NEW, NAME_NEW);
    }
}
