package it.unicam.cs.mpgc.rpg130397.utils;

import javafx.scene.image.Image;

import java.util.Objects;

public class GetSpriteByName {
    public static Image getSprite(String name)
    {
        return new Image(Objects.requireNonNull(GetSpriteByName.class.getResourceAsStream("/sprites/" + name + ".png")));
    }
}
