package it.unicam.cs.mpgc.rpg130397.elements.abstractelements;

import it.unicam.cs.mpgc.rpg130397.elements.objects.Weapon;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/// Contains the weapons of the player. Implemented because of the clean code rule "prefer classes to primitive data structures" and
/// because it guarantees the code to be more expansible
public class Inventory {
    private final Map<Characteristics.CharacteristicType, Weapon> map;

    public Inventory(Map<Characteristics.CharacteristicType, Weapon> inventory) {
        this.map = inventory;
    }
    public Inventory(List<Weapon> weapons) {
        map = new HashMap<>();
        weapons.forEach(this::addWeapon);
    }

    public Collection<Weapon> getWeapons()
    {
        return map.values();
    }
    public void addWeapon(Weapon w)
    {
        map.put(w.getStats().getWeaponType(), w);
    }
    public Weapon getWeapon(Characteristics.CharacteristicType type)
    {
        return map.get(type);
    }

}

