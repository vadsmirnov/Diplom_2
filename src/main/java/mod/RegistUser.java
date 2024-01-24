package mod;

import lombok.Data;

@Data
public class RegistUser {
    private final String email;
    private final String password;
    private final String name;
}
