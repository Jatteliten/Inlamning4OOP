package Utilities;

import java.io.Serializable;

public class AvatarProperties implements Serializable {
    int cat;
    int eyes;
    int mouth;
    int pattern;
    int accessory;
    int headWear;

    public AvatarProperties(int cat, int eyes, int mouth, int pattern, int accessory, int headWear) {
        this.cat = cat;
        this.eyes = eyes;
        this.mouth = mouth;
        this.pattern = pattern;
        this.accessory = accessory;
        this.headWear = headWear;
    }

    public int getCat() {
        return cat;
    }

    public void setCat(int cat) {
        this.cat = cat;
    }

    public int getEyes() {
        return eyes;
    }

    public void setEyes(int eyes) {
        this.eyes = eyes;
    }

    public int getMouth() {
        return mouth;
    }

    public void setMouth(int mouth) {
        this.mouth = mouth;
    }

    public int getPattern() {
        return pattern;
    }

    public void setPattern(int pattern) {
        this.pattern = pattern;
    }

    public int getAccessory() {
        return accessory;
    }

    public void setAccessory(int accessory) {
        this.accessory = accessory;
    }

    public int getHeadWear() {
        return headWear;
    }

    public void setHeadWear(int headWear) {
        this.headWear = headWear;
    }
}
