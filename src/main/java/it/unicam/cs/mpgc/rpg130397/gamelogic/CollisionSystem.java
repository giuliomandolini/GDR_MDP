package it.unicam.cs.mpgc.rpg130397.gamelogic;

import it.unicam.cs.mpgc.rpg130397.controllers.GameController;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.elements.entities.GameObject;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Player;
import it.unicam.cs.mpgc.rpg130397.elements.objects.Bullet;
import it.unicam.cs.mpgc.rpg130397.views.GameObjectView;

import javax.swing.text.PlainView;
import java.util.*;
import java.util.stream.Collectors;

/// Manages and stores collisions in the scene. in particular: Player and enemies, Bullets and Player, and Bullets and Enemies.
public class CollisionSystem {

    private static Set<GameObject> playerCollisions;
    private static Map<Enemy, List<Bullet>> enemyBulletCollisions;

    public static void resetCollisions()
    {
        playerCollisions = new HashSet<>();
        enemyBulletCollisions = new HashMap<>();
    }

    /// Updates all the useful collisions, as those between player and enemies, player and enemy bullets and enemies and player bullets
    public static void checkForCollisions() {
        resetCollisions();

        GameObjectView<Player> player = GameController.getPlayer();
        List<GameObjectView<Enemy>> enemies = GameController.getViews(Enemy.class);
        List<GameObjectView<Bullet>> bullets = GameController.getViews(Bullet.class);

        //collisions between player and enemies
        playerCollisions.addAll(
                enemies.stream()
                        .filter(e -> collision(e, player))
                        .map(GameObjectView::getObject)
                        .collect(Collectors.toSet())
        );

        //collisions between player and enemy bullets
        playerCollisions.addAll(
                bullets.stream()
                        .filter(v -> collision(v, player))
                        .map(GameObjectView::getObject)
                        .filter(b -> b.getSpawner() instanceof Enemy)
                        .collect(Collectors.toSet())
        );

        //collisions between bullets and (enemies and player)
        for(GameObjectView<Bullet> b : bullets)
        {
            //if the bullet is instantiated by an enemy, it has to check if it collides with the player, and vice versa
            if(b.getObject().getSpawner() instanceof Enemy)
            {
                if(collision(b, player)){
                    playerCollisions.add(b.getObject());
                }
            }
            else
            {
                for(GameObjectView<Enemy> e : enemies)
                {
                    if(collision(b, e))
                    {
                        if(enemyBulletCollisions.get(e.getObject()) == null)
                            enemyBulletCollisions.put(e.getObject(), new ArrayList<>());
                        enemyBulletCollisions.get(e.getObject()).add(b.getObject());
                    }
                }
            }

        }

    }
    private static boolean collision(GameObjectView<?> i1, GameObjectView<?> i2)
    {
        return i1.getBoundsInParent().intersects(i2.getBoundsInParent());
    }

    public static Set<GameObject> getPlayerCollisions() {
        return playerCollisions;
    }

    //it could have been an alternative to use T extends GameObject, but then it would have been impossible to get
    //all the elements that extend interactable, that is an interface and cannot extend GameObject
    public static <T> Set<T> getPlayerCollisions(Class<T> type)
    {
        return playerCollisions.stream()
                .filter(g -> g.getClass().equals(type))
                .map(g -> (T)g)
                .collect(Collectors.toSet());
    }

    public static Map<Enemy, List<Bullet>> getEnemyBulletCollisions() {
        return enemyBulletCollisions;
    }
}
