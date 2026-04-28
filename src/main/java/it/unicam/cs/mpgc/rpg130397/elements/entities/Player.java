package it.unicam.cs.mpgc.rpg130397.elements.entities;

import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Characteristics;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.EntityStats;
import it.unicam.cs.mpgc.rpg130397.elements.objects.Weapon;

import java.util.HashMap;
import java.util.Map;

public class Player extends Entity{

    Characteristics characteristics;
    Map<Characteristics.CharacteristicType, Weapon> inventory = new HashMap<>();

    public Player(String name, float health, float speed,  Map<Characteristics.CharacteristicType, Weapon> inventory) {
        super(name, health, speed);
        this.inventory = inventory;
        characteristics = new Characteristics(10, 10, 10);
    }

    public void update()
    {
        for(Weapon w : inventory.values()) w.attack();
    }

    public void changeHealth(float amount){
        getStats().set(EntityStats.StatType.CURRENT_HEALTH, amount);
    }

    public void assignWeapon(Weapon weapon)
    {
        if(weapon == null) throw new IllegalArgumentException("Assegnando un'arma nulla");
        weapon.updateDamage(characteristics);
        inventory.put(weapon.getStats().getWeaponType(), weapon);
    }

    public void setInventory(Map<Characteristics.CharacteristicType, Weapon> inventory) {
        this.inventory = inventory;
    }

    public Map<Characteristics.CharacteristicType, Weapon> getInventory() {
        return inventory;
    }
}
