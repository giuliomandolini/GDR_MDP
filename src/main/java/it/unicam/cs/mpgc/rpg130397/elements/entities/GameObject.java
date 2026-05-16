package it.unicam.cs.mpgc.rpg130397.elements.entities;

import it.unicam.cs.mpgc.rpg130397.utils.GetResourceByName;
import javafx.scene.image.ImageView;

public class GameObject extends ImageView {
    private String name;

    public GameObject(String name)
    {
        if(name == null || name.isEmpty()) throw new IllegalArgumentException("Il nome di un oggetto non può essere nullo o vuoto");

        super(GetResourceByName.getResourcePath(name));
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
