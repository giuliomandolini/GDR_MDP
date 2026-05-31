package it.unicam.cs.mpgc.rpg130397.elements.abstractelements;

/// Contains the stats of the weapon that the player can use to attack the enemies.
/// The weapon can be ranged or not, and from it depends if bulletStats is null or not.
/// Each weapon has an associated CharacteristicType that modifies its damage.
public class WeaponStats {
    private float damage;
    private long cooldown;
    private final float range;
    private final float area;
    private final BulletStats bulletStats;

    //weaponType defines the characteristic that uses that weapon. this is needed to avoid
    //creating new classes in the case a new characteristic is inserted. Doing so it is sufficient
    //to write the logic here in a new function instead of creating a new class for each characteristic.
    //The code remains readable, scalable and not duplicated.
    private final Characteristics.CharacteristicType weaponType;

    //WeaponStats are mainly given by the Json. there are occasions in witch the stats have to be copied from the original
    //ones found in GameData and their damage and cooldown have to be updated, but they have to be a copy because else
    //the logic would override the original data.
    public WeaponStats(WeaponStats copy)
    {
        this.weaponType = copy.weaponType;
        this.area = copy.area;
        this.range = copy.range;
        this.cooldown = copy.cooldown;
        this.damage = copy.damage;
        this.bulletStats = copy.bulletStats;
    }

    public float getDamage() {
        return damage;
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

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public void setCooldown(long cooldown) {
        this.cooldown = cooldown;
    }
}
