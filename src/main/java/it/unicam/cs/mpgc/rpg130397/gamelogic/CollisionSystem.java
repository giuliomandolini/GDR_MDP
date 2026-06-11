package it.unicam.cs.mpgc.rpg130397.gamelogic;

import it.unicam.cs.mpgc.rpg130397.controllers.GameController;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.elements.entities.GameObject;
import it.unicam.cs.mpgc.rpg130397.elements.entities.player.Player;
import it.unicam.cs.mpgc.rpg130397.elements.objects.Bullet;
import it.unicam.cs.mpgc.rpg130397.views.GameObjectView;

import java.util.*;
import java.util.stream.Collectors;

/// Manages and stores collisions in the scene. <br>
/// In particular: all the player collisions (bullets, enemies, interactables, ecc) and those between
/// enemies and player bullets.
public class CollisionSystem {

    private static Set<GameObject> playerCollisions;
    private static Map<Bullet, Enemy> bulletEnemyCollisions;

    public static void resetCollisions()
    {
        playerCollisions = new HashSet<>();
        bulletEnemyCollisions = new HashMap<>();
    }

    /// Updates all the useful collisions, as those between player and enemies, player and enemy bullets and enemies and player bullets
    public static void checkForCollisions() {
        resetCollisions();

        //collisions between players and other objects (except from itself)
        GameObjectView<Player> player = GameController.getPlayer();
        playerCollisions.addAll(
                GameController.getAllViews().stream()
                        .filter(v -> collision(v, player))
                        .map(GameObjectView::getObject)
                        .collect(Collectors.toSet())
        );
        playerCollisions.remove(GameData.getPlayer());

        //collisions between bullets and enemies
        List<GameObjectView<Enemy>> enemies = GameController.getViews(Enemy.class);
        List<GameObjectView<Bullet>> bullets = GameController.getViews(Bullet.class);
        for(GameObjectView<Bullet> b : bullets)
        {
            for(GameObjectView<Enemy> e : enemies)
            {
                if(collision(b, e) && b.getObject().getSpawner() instanceof Player)
                {
                    bulletEnemyCollisions.put(b.getObject(), e.getObject());
                    break;
                }
            }

        }

    }
    /// Checks if a GameObjectView collides with another.
    /// @param i1 the first object
    /// @param i2 the second object
    /// @return true if the objects collide, false otherwise
    private static boolean collision(GameObjectView<?> i1, GameObjectView<?> i2)
    {
        return i1.getBoundsInParent().intersects(i2.getBoundsInParent());
    }

    public static Set<GameObject> getPlayerCollisions() {
        return playerCollisions;
    }

    //it could have been an alternative to use T extends GameObject, but then it would have been impossible to get
    //all the elements that extend interactable, that is an interface and cannot extend GameObject
    /// Returns all the collisions with the player of a given type
    /// @param type The type of class that collides with the player.
    /// @return The set of Objects that collide with the player of the given type.
    public static <T> Set<T> getPlayerCollisions(Class<T> type)
    {
        return playerCollisions.stream()
                .filter(g -> type.isInstance(g))
                .map(g -> (T)g)
                .collect(Collectors.toSet());
    }

    public static Map<Bullet, Enemy> getBulletEnemyCollisions() {
        return bulletEnemyCollisions;
    }
}
