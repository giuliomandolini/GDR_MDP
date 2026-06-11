package it.unicam.cs.mpgc.rpg130397.elements.entities.player;

import java.util.HashMap;
import java.util.Map;

/** Class that contains the
 * fundamental characteristics used only by the Player: <br>
 *  -Strength <br>
 *  -Dexterity<br>
 *  -Intelligence <br>
 *  They have to be non-negative.
 *  Each characteristic modifies the damage of the corresponding weapon.
 */
public class Characteristics {

    //The characteristics are only used by the player, and they must have a link
    //to the javafx pane to update the stats on the ui
    private final Map<CharacteristicType, Integer> characteristics;

    public Characteristics(int strength, int dexterity, int intelligence)
    {
        characteristics = new HashMap<>();
        if (strength < 0 || dexterity < 0 || intelligence < 0) {
            throw new IllegalArgumentException("Characteristics values cannot be less than 0");
        }
        characteristics.put(CharacteristicType.STRENGTH, strength);
        characteristics.put(CharacteristicType.DEXTERITY, dexterity);
        characteristics.put(CharacteristicType.INTELLIGENCE, intelligence);
    }

    public enum CharacteristicType{
        STRENGTH,
        DEXTERITY,
        INTELLIGENCE
    }

    public int getCharacteristicValue(CharacteristicType type) {
        return characteristics.get(type);
    }

    public void setCharacteristicValue(CharacteristicType type, int value) {
        if(value < 0) throw new IllegalArgumentException("Characteristics values cannot be less than 0");
        characteristics.put(type, value);
    }

}
