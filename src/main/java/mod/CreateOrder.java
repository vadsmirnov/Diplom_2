package mod;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrder {
    private final List<String> ingredients;
}