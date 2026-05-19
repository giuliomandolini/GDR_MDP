package it.unicam.cs.mpgc.rpg130397.controllers;

import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Position;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Player;
import it.unicam.cs.mpgc.rpg130397.elements.objects.Bullet;
import it.unicam.cs.mpgc.rpg130397.gamelogic.*;
import it.unicam.cs.mpgc.rpg130397.views.BulletView;
import it.unicam.cs.mpgc.rpg130397.views.EnemyView;
import it.unicam.cs.mpgc.rpg130397.views.PlayerView;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

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
        GameData.start(gamePane); //1
        Player playerModel = new Player("Player", 10, 10, JDeserializer.getPreviousInventory(), new Position()); //2

        player = new PlayerView(playerModel);
        GameData.setPlayer(playerModel);//3

        //it is better to use linked lists instead of array lists because a there are a lot of additions and remotions
        bullets = new LinkedList<>();
        enemies = new LinkedList<>();


        playerModel.getPosition().move(400 , 400);

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
        createDestroyEnemyViews();
        createDestroyBulletViews();
        updatePositions();

        CollisionSystem.checkForCollisions(bullets, enemies, player);
        GameManager.update();
    }

    private void updatePositions() {
        updateEnemiesPosition();
        updatePlayerPosition();
        updateBulletsPosition();
    }

    private void createDestroyEnemyViews()
    {
        for(Enemy e : GameData.getEnemiesToSpawn())
        {
            addEnemy(new EnemyView(e));
        }
        for(Enemy e : GameData.getEnemiesToDespawn())
        {
            remove(e);
        }
    }
    private void createDestroyBulletViews()
    {
        for(Bullet b : GameData.getBulletsToSpawn())
        {
            addBullet(new BulletView(b));
        }
        for(Bullet b : GameData.getBulletsToDespawn())
        {
            remove(b);
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