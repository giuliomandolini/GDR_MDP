package it.unicam.cs.mpgc.rpg130397.views;

import it.unicam.cs.mpgc.rpg130397.elements.entities.GameObject;
import it.unicam.cs.mpgc.rpg130397.utils.GetSpriteByName;
import javafx.scene.image.ImageView;

/// Base class from where all the views originate
public abstract class GameObjectView extends ImageView {

    public GameObjectView(GameObject object)
    {
        super(GetSpriteByName.getSprite(object.getName()));
    }

    public abstract GameObject getObject();
    public abstract void update();

}
