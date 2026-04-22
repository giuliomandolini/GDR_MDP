package it.unicam.cs.mpgc.rpg130397.elements.abstractelements;

import java.util.HashMap;
import java.util.Map;

/** Class that contains the
 * fundamental characteristics used only by the Player: <br>
 *  -Strength <br>
 *  -Dexterity<br>
 *  -Intelligence
 */
public class Characteristics {

    public Characteristics(int strength, int dexterity, int intelligence)
    {
        characteristics.put(CharacteristicType.STRENGTH, strength);
        characteristics.put(CharacteristicType.DEXTERITY, dexterity);
        characteristics.put(CharacteristicType.INTELLIGENCE, intelligence);
    }

    //Hashmap per memorizzare le statistiche di una entità nel gioco
    private final Map<CharacteristicType, Integer> characteristics = new HashMap<>();

    public enum CharacteristicType{
        STRENGTH,
        DEXTERITY,
        INTELLIGENCE
    }

    public int getCharacteristic(CharacteristicType type) {
        return characteristics.get(type);
    }

    public void setCharacteristic(CharacteristicType type, int value) {
        if(value < 0) throw new IllegalArgumentException("Valore di una caratteristica minore di 0");
        characteristics.replace(type, value);
    }

}
