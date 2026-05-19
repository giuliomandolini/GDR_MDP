package it.unicam.cs.mpgc.rpg130397.gamelogic;

import it.unicam.cs.mpgc.rpg130397.controllers.GameController;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Entity;
import it.unicam.cs.mpgc.rpg130397.elements.objects.Bullet;

import java.util.*;

public class CombatSystem {

    public static void update()
    {
        for(Bullet b : CollisionSystem.getPlayerBulletCollisions())
        {
            GameData.getPlayer().changeHealth(-b.getDamage());
        }
    }
}
