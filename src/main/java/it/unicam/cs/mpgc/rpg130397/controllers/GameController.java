package it.unicam.cs.mpgc.rpg130397.controllers;

import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Characteristics;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Position;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.WeaponStats;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Player;
import it.unicam.cs.mpgc.rpg130397.elements.objects.Bullet;
import it.unicam.cs.mpgc.rpg130397.elements.objects.Weapon;
import it.unicam.cs.mpgc.rpg130397.gamelogic.*;
import it.unicam.cs.mpgc.rpg130397.views.BulletView;
import it.unicam.cs.mpgc.rpg130397.views.EnemyView;
import it.unicam.cs.mpgc.rpg130397.views.GameObjectView;
import it.unicam.cs.mpgc.rpg130397.views.PlayerView;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GameController {


    @FXML
    private ProgressBar healthBar;
    @FXML
    private AnchorPane gamePane;

    private PlayerView player;
    private List<BulletView> bullets;
    private List<EnemyView> enemies;



    @FXML
    public void initialize() throws FileNotFoundException, InterruptedException {
        Player playerModel = new Player("Player", 10, 10, JDeserializer.getPreviousInventory(), new Position());
        System.out.println(playerModel.getInventory().get(Characteristics.CharacteristicType.STRENGTH).getStats());
        System.out.println(playerModel.getInventory().get(Characteristics.CharacteristicType.DEXTERITY).getName());
        System.out.println(playerModel.getInventory().get(Characteristics.CharacteristicType.INTELLIGENCE).getName());
        player = new PlayerView(playerModel);
        GameData.start(player.getPlayer(), gamePane);

        System.out.println(gamePane.getMinHeight() + " + " + gamePane.getMinWidth());
        //it is better to use linked lists instead of array lists because a there are a lot of additions and remotions
        bullets = new LinkedList<>();
        enemies = new LinkedList<>();


        Weapon w = new Weapon("Fireball");


        playerModel.getPosition().move(200, 231);
        System.out.println(playerModel);

        add(player);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
            }
        };
        timer.start();
    }

    ///Synchronizes models with views and controls game
    public void update()
    {
        createEnemyViews(SpawnSystem.spawnEnemies());
        updatePositions();
        CollisionSystem.checkForCollisions(bullets, enemies, player);

        updateModels();
    }

    private void updatePositions() {
        updateEnemiesPosition();
        updatePlayerPosition();
        updateBulletsPosition();
    }

    private void createEnemyViews(List<Enemy> enemies)
    {
        if(enemies == null) return;
        for(Enemy e : enemies)
        {
            addEnemy(new EnemyView(e));
        }
    }

    private void updatePlayerPosition()
    {
        player.update();
    }

    private void updateEnemiesPosition()
    {
        for(EnemyView e : enemies)
        {
            e.update();
        }
    }

    private void updateBulletsPosition()
    {
        for(BulletView b : bullets)
        {
            b.update();
        }
    }

    private void updateModels()
    {
        for(Enemy e : GameData.getEnemies())
        {
            e.update();
        }
        for(Bullet b : GameData.getBullets())
        {
            b.update();
        }
        player.getPlayer().update();
    }

    private void createDeleteBulletViews()
    {
//        //view creation control: if a bullet is present in GameData bullets but not in GameController bullets then it has to be instantiated.
//        //collect the existing bullets in a set to use contains instead of anyMatch
//        Set<Bullet> viewBullets =
//                bullets.stream()
//                        .map(BulletView::getBullet) //gets a stream of the objects of the class
//                        .collect(Collectors.toSet());   //transforms it into a set
//
//        Set<Bullet> modelBullets = new HashSet<>(GameData.getBullets());
//
//        Set<Bullet> onlyModel = new HashSet<>(modelBullets);
//        onlyModel.removeAll(viewBullets);
//        Set<Bullet> onlyView = new HashSet<>(viewBullets);
//        onlyView.removeAll(modelBullets);
//
//        for(Bullet b : onlyModel)
//        {
//            BulletView newBullet = new BulletView(b);
//            bullets.add(newBullet);
//            add(newBullet);
//        }
//        for(Bullet b : onlyView)
//        {
//            //redefined equals and hashcode
//            BulletView toRemove = new BulletView(b);
//            bullets.remove(toRemove);
//            remove(toRemove);
//        }
    }

    //Same as createBullets. Avoiding duplicating code would have complicated enormously the architecture

    private void createDeleteEnemyViews()
    {

//        Set<Enemy> viewEnemies =
//                enemies.stream()
//                        .map(EnemyView::getEnemy)
//                        .collect(Collectors.toSet());
//        Set<Enemy> modelEnemies = new HashSet<>(GameData.getEnemies());
//
//        Set<Enemy> onlyModel = new HashSet<>(modelEnemies);
//        System.out.println(modelEnemies.size() + " " + onlyModel.size());
//        onlyModel.removeAll(viewEnemies);
//        Set<Enemy> onlyView = new HashSet<>(viewEnemies);
//        onlyView.removeAll(modelEnemies);
//
//        System.out.println(GameData.getEnemies().size() + ", " + onlyModel.size() + ", " + onlyView.size());
//
//        for(Enemy b : onlyModel)
//        {
//            EnemyView newEnemy = new EnemyView(b);
//            enemies.add(newEnemy);
//            add(newEnemy);
//        }
//        for(Enemy b : onlyView)
//        {
//            //redefined equals and hashcode
//            EnemyView toRemove = new EnemyView(b);
//            enemies.remove(toRemove);
//            remove(toRemove);
//        }
    }



    private void add(Node object)
    {
        gamePane.getChildren().add(object);
    }
    private void addEnemy(EnemyView object)
    {
        gamePane.getChildren().add(object);
        enemies.add(object);
    }
    private void addBullet(BulletView object)
    {
        gamePane.getChildren().add(object);
        bullets.add(object);
    }
    public void add(Node object, Position spawnPosition)
    {
        gamePane.getChildren().add(object);
    }
    public void remove(Node object)
    {
        gamePane.getChildren().remove(object);
    }

}