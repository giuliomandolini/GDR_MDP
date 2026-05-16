package it.unicam.cs.mpgc.rpg130397.views;

import it.unicam.cs.mpgc.rpg130397.elements.entities.GameObject;
import it.unicam.cs.mpgc.rpg130397.utils.GetSpriteByName;
import javafx.scene.image.ImageView;

public abstract class GameObjectView extends ImageView {
    GameObject object;

    public GameObjectView(GameObject object)
    {
        this.object = object;
        super(GetSpriteByName.getSprite(object.getName()));
    }

    public void update()
    {
        setLayoutX(object.getPosition().getX());
        setLayoutY(object.getPosition().getY());
    }

}
