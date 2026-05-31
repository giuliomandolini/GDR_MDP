package it.unicam.cs.mpgc.rpg130397.elements.entities;

import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Position;
import it.unicam.cs.mpgc.rpg130397.gamelogic.SpawnSystem;

/// Base class for each element that exists in the game. Each element must have a name and a position,
/// and it is identified by its id.
public abstract class GameObject {
    private final String name;
    private transient Position position;
    //id is needed to identify GameObjects instantiated runtime to avoid having duplicate children on javafx gamePane
    private final transient int id;

    public GameObject(String name, Position position)
    {
        if(name == null || name.isEmpty()) throw new IllegalArgumentException("Il nome di un oggetto non può essere nullo o vuoto");

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
