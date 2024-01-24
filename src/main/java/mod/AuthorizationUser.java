package mod;

import lombok.Data;

@Data
public class AuthorizationUser {
    private final String email;
    private final String password;
}
