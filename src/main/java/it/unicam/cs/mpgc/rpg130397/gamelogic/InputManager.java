package it.unicam.cs.mpgc.rpg130397.gamelogic;

import javafx.scene.input.KeyCode;

import java.util.HashSet;
import java.util.Set;

/**
 * This class is responsable for managing the input coming from the player, such as UI inputs and keyboard inputs.
 */
public class InputManager {
    public static Set<KeyCode> pressedKeys;
    public static int x;
    public static int y;

    public static void start()
    {
        pressedKeys = new HashSet<>();
    }

    public static void keyPressed(KeyCode k)
    {
        pressedKeys.add(k);
    }
    public static void keyReleased(KeyCode k)
    {
        pressedKeys.remove(k);
    }

    ///Right positive, left negative
    public static int getX()
    {
        int result = 0;
        if(pressedKeys.contains(KeyCode.D)) result++;
        if(pressedKeys.contains(KeyCode.A)) result--;
        return result;
    }
    ///Down positive, up negative
    public static int getY()
    {
        int result = 0;
        if(pressedKeys.contains(KeyCode.S)) result++;
        if(pressedKeys.contains(KeyCode.W)) result--;
        return result;
    }

}
