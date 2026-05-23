package it.unicam.cs.mpgc.rpg130397.elements.objects;

import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Interactable;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Position;
import it.unicam.cs.mpgc.rpg130397.elements.entities.GameObject;

/// TODO reward obtained from killing enemies; could contain various new weapons, upgrades for weapons or characteristics or potions
public class Chest extends GameObject implements Interactable {

    public Chest(String sprite, Position position)
    {
        super(sprite, position);
    }

    public void interact()
    {

    }
}
