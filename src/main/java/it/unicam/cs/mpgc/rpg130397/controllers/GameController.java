package it.unicam.cs.mpgc.rpg130397.controllers;

import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Position;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Player;
import it.unicam.cs.mpgc.rpg130397.elements.objects.Bullet;
import it.unicam.cs.mpgc.rpg130397.elements.objects.Weapon;
import it.unicam.cs.mpgc.rpg130397.gamelogic.GameData;
import it.unicam.cs.mpgc.rpg130397.gamelogic.JDeserializer;
import it.unicam.cs.mpgc.rpg130397.views.EnemyView;
import it.unicam.cs.mpgc.rpg130397.views.GameObjectView;
import it.unicam.cs.mpgc.rpg130397.views.PlayerView;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.FileNotFoundException;
import java.util.List;

import static it.unicam.cs.mpgc.rpg130397.gamelogic.GameData.getEnemiesMap;
import static it.unicam.cs.mpgc.rpg130397.gamelogic.GameData.getPlayer;

public class GameController {


    @FXML
    private ProgressBar healthBar;
    @FXML
    private AnchorPane gamePane;

    private GameObjectView<Player> player;
    private List<GameObjectView<Bullet>> bullets;

    @FXML
    public void initialize() throws FileNotFoundException {
        System.out.println(getEnemiesMap());
        Player playerModel = new Player("Player", 10, 10, JDeserializer.getPreviousInventory(), new Position());
        player = new GameObjectView<>(playerModel);

        Weapon w = new Weapon("Fireball");

        player.setLayoutX(10);
        player.setLayoutY(10);

        Enemy skeletonModel = GameData.getEnemy("Skeleton Warrior");
        GameObjectView<Enemy> skeleton = new GameObjectView<>(skeletonModel);
        add(skeleton);
        add(player);
    }

    public void checkForBullets()
    {
        for(GameObjectView<Bullet> b : bullets)
        {
            if(b.getObject().getSpawner() instanceof Enemy)
                if(collision(b, player)){
                    //sberla
                }


        }
    }

    public void add(Node object)
    {
        gamePane.getChildren().add(object);
    }
    private boolean collision(GameObjectView<?> i1, GameObjectView<?> i2)
    {
        //controllo se la distanza è irraggiungibile per alleggerire il carico di intersects che è molto maggiore
        if(i1.getObject().getPosition().distanceFrom(i2.getObject().getPosition()) > 150) return false;
        return i1.getBoundsInParent().intersects(i2.getBoundsInParent());
    }
}