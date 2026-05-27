package it.unicam.cs.mpgc.rpg130397.views;

import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Position;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.elements.entities.GameObject;
import it.unicam.cs.mpgc.rpg130397.utils.GetSpriteByName;
import it.unicam.cs.mpgc.rpg130397.utils.ScreenToWorldPoint;
import javafx.scene.image.ImageView;

/// Base class from where all the views originate
public class GameObjectView<T extends GameObject> extends ImageView {

    private final T object;

    public GameObjectView(T object)
    {
        super(GetSpriteByName.getSprite(object.getName()));
        this.object = object;
    }

    public T getObject()
    {
        return object;
    }
    public void update()
    {
        Position newPos = ScreenToWorldPoint.worldToScreen(object.getPosition());
        setLayoutX(newPos.getX());
        setLayoutY(newPos.getY());
    }

    @Override
    public boolean equals(Object o)
    {
        if(!(o instanceof GameObjectView<?>)) return false;

        return ((GameObjectView<?>) o).object.equals(object);
    }

    @Override
    public int hashCode()
    {
        return object.hashCode();
    }

}
