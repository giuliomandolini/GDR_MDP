package it.unicam.cs.mpgc.rpg130397.elements.entities;

import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Position;
import it.unicam.cs.mpgc.rpg130397.utils.GetSpriteByName;
import javafx.scene.image.ImageView;

/// Base class for each element that exists in the game. Each element must have a name and a position.
public abstract class GameObject {
    private final String name;
    private transient Position position;

    public GameObject(String name, Position position)
    {
        if(name == null || name.isEmpty()) throw new IllegalArgumentException("Il nome di un oggetto non può essere nullo o vuoto");

        this.position = position;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
