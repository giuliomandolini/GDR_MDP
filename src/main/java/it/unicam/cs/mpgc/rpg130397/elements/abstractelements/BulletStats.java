package it.unicam.cs.mpgc.rpg130397.elements.abstractelements;

public class BulletStats {
    private String name;
    private float speed;

    public BulletStats(String name, float speed) {
        this.name = name;
        this.speed = speed;
    }

    public String getName() {
        return name;
    }

    public float getSpeed() {
        return speed;
    }

    //Damage and other data is given by the spawner
}
