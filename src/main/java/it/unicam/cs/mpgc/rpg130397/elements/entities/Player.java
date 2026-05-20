package it.unicam.cs.mpgc.rpg130397.elements.entities;

import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Characteristics;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.EntityStats;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Position;
import it.unicam.cs.mpgc.rpg130397.elements.objects.Weapon;
import it.unicam.cs.mpgc.rpg130397.gamelogic.GameData;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;

import java.util.Map;

public class Player extends Entity{

    private Characteristics characteristics;
    private Map<Characteristics.CharacteristicType, Weapon> inventory;
    private FloatProperty healthProperty;

    public Player(String name, float health, float speed, Map<Characteristics.CharacteristicType, Weapon> inventory, Characteristics characteristics, Position position) {
        super(name, health, speed, position);
        this.inventory = inventory;

        GameData.setPlayer(this);//before
        this.characteristics = characteristics;
        healthProperty = new SimpleFloatProperty(health);
        GameData.setPlayer(this);
        System.out.println("player with health: " + getStats().get(EntityStats.StatType.CURRENT_HEALTH) + " out of " + getStats().get(EntityStats.StatType.MAX_HEALTH));
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

    public FloatProperty getHealthProperty() {
        return healthProperty;
    }

    public Characteristics getCharacteristics() {
        return characteristics;
    }

    public void increaseCharacteristic(Characteristics.CharacteristicType type, int amount)
    {
        characteristics.setCharacteristicValue(type, characteristics.getCharacteristicValue(type) + amount);
    }

    @Override
    public void changeHealth(float amount) {
        super.changeHealth(amount);
        //sets newHealth positive
        float newHealth = Math.max(getStats().get(EntityStats.StatType.CURRENT_HEALTH), 0);
        healthProperty.set(newHealth);
    }
}
