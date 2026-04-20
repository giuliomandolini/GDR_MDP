package it.unicam.cs.mpgc.rpg130397;

public class Player extends Entity{

    Characteristics characteristics;

    public Player(float health, float speed, float basicDamage) {
        super(health, speed, basicDamage);
        characteristics = new Characteristics(10, 10, 10);
    }

}
