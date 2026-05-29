package it.unicam.cs.mpgc.rpg130397.views;

import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Position;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.elements.entities.GameObject;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Player;
import it.unicam.cs.mpgc.rpg130397.utils.GetSpriteByName;
import it.unicam.cs.mpgc.rpg130397.utils.ScreenToWorldPoint;
import javafx.scene.image.ImageView;

/// Base class from where all the views originate.
//it uses the generics because else there would be a view class for each GameObject in game and the project would not
//be scalable, doing so permits to instantiate a new class simply creating a new GameObjectView with the interested GameObject
//as T, without using a cast if T would only be a simple GameObject.
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
        //if the GameObjectView is the player, it needs to remain at the center of the screen and doesn't need to move.
        //the player is the only case it happens, because all the other objects are moving relative to the world so it doesn't
        //ruin the scalability.
        if(object instanceof Player) return;

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
