package it.unicam.cs.mpgc.rpg130397.elements.entities;

import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Characteristics;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Stats;
import it.unicam.cs.mpgc.rpg130397.elements.objects.Weapon;

import java.util.HashMap;
import java.util.Map;

public class Player extends Entity{

    Characteristics characteristics;
    Map<Characteristics.CharacteristicType, Weapon> weapons = new HashMap<>();

    public Player(String name, float health, float speed) {
        super(name, health, speed);
        characteristics = new Characteristics(10, 10, 10);
    }

    public void update()
    {
        for(Weapon w : weapons.values()) w.attack();
    }

    public void changeHealth(float amount){
        getStats().set(Stats.StatType.CURRENT_HEALTH, amount);
    }

}
