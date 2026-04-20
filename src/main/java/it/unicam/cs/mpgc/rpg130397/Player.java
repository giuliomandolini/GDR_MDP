package it.unicam.cs.mpgc.rpg130397;

import java.util.HashMap;
import java.util.Map;

public class Player extends Entity{

    Characteristics characteristics;
    Map<Characteristics.CharacteristicType, Weapon> weapons = new HashMap<>();

    public Player(float health, float speed, float basicDamage) {
        super(health, speed, basicDamage);
        characteristics = new Characteristics(10, 10, 10);
    }

    public void Update()
    {
        for(Weapon w : weapons.values()) w.attack();
    }


}
