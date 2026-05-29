package it.unicam.cs.mpgc.rpg130397.elements.entities;

import it.unicam.cs.mpgc.rpg130397.controllers.GameController;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Characteristics;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.EntityStats;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Position;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Updatable;
import it.unicam.cs.mpgc.rpg130397.elements.objects.Weapon;
import it.unicam.cs.mpgc.rpg130397.gamelogic.GameData;
import it.unicam.cs.mpgc.rpg130397.gamelogic.GameManager;
import it.unicam.cs.mpgc.rpg130397.gamelogic.InputManager;
import it.unicam.cs.mpgc.rpg130397.gamelogic.JDeserializer;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public class Player extends Entity implements Updatable {

    private final Characteristics characteristics;
    private final Map<Characteristics.CharacteristicType, Weapon> inventory;
    private final FloatProperty healthProperty;

    public Player() throws FileNotFoundException {
        super("Player", 500, 2.2f, new Position(0, 0));

        GameData.setPlayer(this);//before
        this.characteristics =  new Characteristics(10, 10, 10);
        this.inventory = JDeserializer.getPreviousInventory();//after

        healthProperty = new SimpleFloatProperty(getStats().get(EntityStats.StatType.MAX_HEALTH));
 }

    public void update()
    {
        //Attack
        for(Weapon w : inventory.values()) w.attack();
        //Move
        getPosition().move(InputManager.getX() * getStats().get(EntityStats.StatType.SPEED), InputManager.getY() * getStats().get(EntityStats.StatType.SPEED));
    }

    public void assignWeapon(Weapon weapon)
    {
        if(weapon == null) throw new IllegalArgumentException("Assegnando un'arma nulla");
        weapon.setLevel(GameData.getWeaponLevel(weapon.getName()));
        weapon.updateDamage(characteristics);
        inventory.put(weapon.getStats().getWeaponType(), weapon);
        GameController.updateUi();
    }

    protected void die() {
        try {
            GameManager.lose();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
