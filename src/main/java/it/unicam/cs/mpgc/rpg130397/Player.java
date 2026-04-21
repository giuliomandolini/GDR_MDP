package it.unicam.cs.mpgc.rpg130397;

import java.util.HashMap;
import java.util.Map;

public class Player extends Entity{

    Characteristics characteristics;
    Map<Characteristics.CharacteristicType, Weapon> weapons = new HashMap<>();

    public Player(float health, float speed) {
        super(health, speed);
        characteristics = new Characteristics(10, 10, 10);
    }

    public void update()
    {
        for(Weapon w : weapons.values()) w.attack();
    }

    public void changeHealth(float amount){
        stats.set(Stats.StatType.CURRENT_HEALTH, amount);
    }

}
