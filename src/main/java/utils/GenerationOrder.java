package utils;

import mod.CreateOrder;
import java.util.List;
import static utils.GenerationDate.randomString;

public class GenerationOrder {
    final static String DEFAULT_INGREDIENT_HASH = "61c0c5a71d1f82001bdaaa6d";
    final static String INVALID_INGREDIENT_HASH = randomString(4);

    public static CreateOrder getDefaultOrder() {
        return new CreateOrder(List.of(DEFAULT_INGREDIENT_HASH));
    }

    public static CreateOrder getOrderWithoutIngredients() {
        return new CreateOrder(List.of());
    }

    public static CreateOrder getOrderWithInvalidHash() {
        return new CreateOrder(List.of(INVALID_INGREDIENT_HASH));
    }
}
