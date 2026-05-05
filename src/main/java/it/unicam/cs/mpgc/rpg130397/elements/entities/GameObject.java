package it.unicam.cs.mpgc.rpg130397.elements.entities;

import it.unicam.cs.mpgc.rpg130397.utils.GetResourceByName;

public class GameObject {
    private String sprite;
    private String name;

    public GameObject(String name)
    {
        if(name == null || name.isEmpty()) throw new IllegalArgumentException("Il nome di un oggetto non può essere nullo o vuoto");
        sprite = GetResourceByName.getResourcePath(name);
        this.name = name;
    }

    public String getSprite() {
        return sprite;
    }

    public String getName() {
        return name;
    }
}
