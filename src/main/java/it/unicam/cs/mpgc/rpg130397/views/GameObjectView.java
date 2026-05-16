package it.unicam.cs.mpgc.rpg130397.views;

import it.unicam.cs.mpgc.rpg130397.elements.entities.GameObject;
import it.unicam.cs.mpgc.rpg130397.utils.GetSpriteByName;
import javafx.scene.image.ImageView;

public class GameObjectView<T extends GameObject> extends ImageView {
    private T object;

    public GameObjectView(T object)
    {
        this.object = object;
        super(GetSpriteByName.getSprite(object.getName()));
    }

    public void update()
    {
        setLayoutX(object.getPosition().getX());
        setLayoutY(object.getPosition().getY());
    }

    public T getObject() {
        return object;
    }
}
