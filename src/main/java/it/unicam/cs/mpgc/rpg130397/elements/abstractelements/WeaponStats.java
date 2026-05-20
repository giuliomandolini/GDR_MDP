package it.unicam.cs.mpgc.rpg130397.elements.abstractelements;

public class WeaponStats {
    private float baseDamage;
    private long cooldown;
    private float range;
    private float area;
    private BulletStats bulletStats;

    //weaponType defines the characteristic that uses that weapon. this is needed to avoid
    //creating new classes in the case a new characteristic is inserted. Doing so it is sufficient
    //to write the logic here in a new function instead of creating a new class for each characteristic.
    //The code remains readable, scalable and not duplicated.
    private Characteristics.CharacteristicType weaponType;

    public WeaponStats(float range, long cooldown, float baseDamage, float area, BulletStats bulletStats,
                       Characteristics.CharacteristicType weaponType) {
        this.weaponType = weaponType;
        this.area = area;
        this.range = range;
        this.cooldown = cooldown;
        this.baseDamage = baseDamage;
        this.bulletStats = bulletStats;
    }

    public float getBaseDamage() {
        return baseDamage;
    }

    public long getCooldown() {
        return cooldown;
    }

    public float getRange() {
        return range;
    }

    public Characteristics.CharacteristicType getWeaponType() {
        return weaponType;
    }

    public float getArea() {
        return area;
    }

    public BulletStats getBulletStats() {
        return bulletStats;
    }
}
