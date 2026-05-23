package it.unicam.cs.mpgc.rpg130397.elements.abstractelements;

/// Class that contains bullet stats that are not given by its spawner. They are only name and speed.
/// (es. damage, range, ecc... are given by the spawner of the bullet)
public class BulletStats {
    private final String name;
    private final float speed;

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
