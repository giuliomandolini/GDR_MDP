package it.unicam.cs.mpgc.rpg130397.elements.abstractelements;

public class WeaponStats {
    private String name;
    private float baseDamage;
    private float cooldown;
    private float range;
    private float area;
    //weaponType defines the characteristic that uses that weapon. this is needed to avoid
    //creating new classes in the case a new characteristic is inserted. Doing so it is sufficient
    //to write the logic here in a new function instead of creating a new class for each characteristic.
    //The code remains readable, scalable and not duplicated.
    private Characteristics.CharacteristicType weaponType;

    public WeaponStats(String name, float range, float cooldown, float baseDamage, float area,
                       Characteristics.CharacteristicType weaponType)
    {
        this.weaponType = weaponType;
        this.area = area;
        this.range = range;
        this.cooldown = cooldown;
        this.baseDamage = baseDamage;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public float getBaseDamage() {
        return baseDamage;
    }

    public float getCooldown() {
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
}
