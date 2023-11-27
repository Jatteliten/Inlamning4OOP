package Utilities;

import java.io.Serializable;

public record AvatarProperties(int cat, int eyes, int mouth, int pattern, int accessory,
                               int headWear) implements Serializable {
}
