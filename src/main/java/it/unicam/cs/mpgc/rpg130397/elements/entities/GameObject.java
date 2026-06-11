package it.unicam.cs.mpgc.rpg130397.elements.entities;

import it.unicam.cs.mpgc.rpg130397.gamelogic.Position;
import it.unicam.cs.mpgc.rpg130397.gamelogic.SpawnSystem;

/// Base class for each element that exists in the game. Each element has an associated sprite, and it must have a name and a position. <br>
/// it is identified by its id.
public abstract class GameObject {
    private final String name;
    private transient Position position;
    //id is needed to identify GameObjects instantiated runtime to avoid having duplicate children on javafx gamePane
    private final transient int id;

    /// @param name The name of the GameObject. Must not be null, and it has to correspond with a sprite name.
    /// @param position The initial position of the GameObject. Must not be null.
    public GameObject(String name, Position position)
    {
        if(name == null || name.isEmpty()) throw new IllegalArgumentException("Object name must not be empty");
        if(position == null) throw new IllegalArgumentException("Object must have a position");

        this.position = position;
        this.name = name;
        this.id = SpawnSystem.getNewId();
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

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof GameObject)) return false;
        return ((GameObject) obj).id == id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
