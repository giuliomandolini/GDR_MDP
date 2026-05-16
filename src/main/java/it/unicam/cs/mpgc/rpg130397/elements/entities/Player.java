package it.unicam.cs.mpgc.rpg130397.elements.entities;

import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Characteristics;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Position;
import it.unicam.cs.mpgc.rpg130397.elements.objects.Weapon;
import it.unicam.cs.mpgc.rpg130397.gamelogic.GameData;

import java.util.HashMap;
import java.util.Map;

public class Player extends Entity{

    Characteristics characteristics;
    Map<Characteristics.CharacteristicType, Weapon> inventory = new HashMap<>();

    public Player(String name, float health, float speed, Map<Characteristics.CharacteristicType, Weapon> inventory, Position position) {
        super(name, health, speed, position);
        this.inventory = inventory;
        characteristics = new Characteristics(10, 10, 10);

        GameData.setPlayer(this);
    }

    public void update()
    {
        for(Weapon w : inventory.values()) w.attack();
    }

    public void assignWeapon(Weapon weapon)
    {
        if(weapon == null) throw new IllegalArgumentException("Assegnando un'arma nulla");
        weapon.updateDamage(characteristics);
        inventory.put(weapon.getStats().getWeaponType(), weapon);
    }

    protected void die()
    {
        //TODO
    }

    public void setInventory(Map<Characteristics.CharacteristicType, Weapon> inventory) {
        this.inventory = inventory;
    }

    public Map<Characteristics.CharacteristicType, Weapon> getInventory() {
        return inventory;
    }

}
