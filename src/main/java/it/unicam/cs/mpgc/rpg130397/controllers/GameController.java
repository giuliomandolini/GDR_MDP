package it.unicam.cs.mpgc.rpg130397.controllers;

import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Characteristics;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.EntityStats;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Position;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.elements.entities.GameObject;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Player;
import it.unicam.cs.mpgc.rpg130397.elements.objects.Bullet;
import it.unicam.cs.mpgc.rpg130397.gamelogic.*;
import it.unicam.cs.mpgc.rpg130397.utils.SceneManager;
import it.unicam.cs.mpgc.rpg130397.utils.ScreenToWorldPoint;
import it.unicam.cs.mpgc.rpg130397.views.BulletView;
import it.unicam.cs.mpgc.rpg130397.views.EnemyView;
import it.unicam.cs.mpgc.rpg130397.views.GameObjectView;
import it.unicam.cs.mpgc.rpg130397.views.PlayerView;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/// Controller of the game scene.
/// It contains the timer and all the root calls of the updates and syncs the views with the models.
public class GameController {
    @FXML
    private Rectangle healthBar;
    @FXML
    private AnchorPane gamePane;
    @FXML
    private Label strengthLabel;
    @FXML
    private Label dexterityLabel;
    @FXML
    private Label intelligenceLabel;
    @FXML
    private Button playAgain;

    private PlayerView player;
    private List<BulletView> bullets;
    private List<EnemyView> enemies;

    private AnimationTimer timer;
    public static boolean lost;

    private static Position mousePosition;
    //cannot be final because javafx and fxml don't call the constructor so they have to be assigned somwhere else
    public static double SCREENWIDTH;
    public static double SCREENHEIGHT;

    @FXML
    public void initialize() throws FileNotFoundException{
        setupGameLogic();
        createPlayer();
        setupUi();
        setupInput();

        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
            }
        };
        timer.start();
    }

    private void setupGameLogic() throws FileNotFoundException {
        lost = false;
        SCREENWIDTH = gamePane.getMinWidth();
        SCREENHEIGHT = gamePane.getMinHeight();
        GameData.start();
        InputManager.start();

        //it is better to use linked lists instead of array lists because a there are a lot of additions and remotions
        bullets = new LinkedList<>();
        enemies = new LinkedList<>();
    }

    private void createPlayer() throws FileNotFoundException {
        Player playerModel = new Player("Player", 250, 2.2f, new Characteristics(10, 10, 10), new Position(0, 0)); //2

        player = new PlayerView(playerModel);
        player.setScaleX(0.8);
        player.setScaleY(0.8);
        addGameObject(player);
    }

    private void setupUi()
    {
        //the button has to be removed or else it would count as a child of gamePane and getNode would iterate also through it
        gamePane.getChildren().remove(playAgain);

        healthBar.widthProperty().bind(player.getObject().getHealthProperty().divide(player.getObject().getStats().get(EntityStats.StatType.MAX_HEALTH) / 155));

        strengthLabel.textProperty().bind(GameData.getPlayer().getCharacteristics().getCharacteristicProperty(Characteristics.CharacteristicType.STRENGTH).asString());
        dexterityLabel.textProperty().bind(GameData.getPlayer().getCharacteristics().getCharacteristicProperty(Characteristics.CharacteristicType.DEXTERITY).asString());
        intelligenceLabel.textProperty().bind(GameData.getPlayer().getCharacteristics().getCharacteristicProperty(Characteristics.CharacteristicType.INTELLIGENCE).asString());

        gamePane.setFocusTraversable(true);
    }

    private void setupInput()
    {
        mousePosition = new Position();
        gamePane.setOnMouseMoved(event -> mousePosition.setPosition(ScreenToWorldPoint.screenToWorld(new Position((float) event.getX(), (float) event.getY()))));
        gamePane.setOnKeyPressed(keyEvent -> InputManager.keyPressed(keyEvent.getCode()));
        gamePane.setOnKeyReleased(keyEvent -> InputManager.keyReleased(keyEvent.getCode()));
    }

    ///Synchronizes models with views and controls game
    public void update()
    {
        if(lost) {
            manageLoss();
            return;
        }
        //views update
        createDestroyEnemyViews();
        createDestroyBulletViews();
        updatePositions();

        //logic update
        GameManager.update(bullets, enemies, player);
    }

    private void updatePositions() {
        player.update();

        for(EnemyView e : enemies)
        {
            e.update();
        }

        for(BulletView b : bullets)
        {
            b.update();
        }
    }

    private void createDestroyEnemyViews()
    {
        for(Enemy e : GameData.getEnemiesToSpawn())
        {
            addGameObject(new EnemyView(e));
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
            addGameObject(new BulletView(b));
        }
        List<Bullet> toDespawn = GameData.getBulletsToDespawn();
        for(Bullet b : toDespawn)
        {
            remove(b);
        }
    }

    public static void lose()
    {
        lost = true;
    }

    private void manageLoss()
    {
        timer.stop();
        remove(player.getObject());
        gamePane.getChildren().add(playAgain);
    }

    @FXML
    private void playAgain() throws IOException {
        SceneManager.loadScene("game");
    }

    private void addGameObject(GameObjectView object)
    {
        gamePane.getChildren().add(object);
        if(object instanceof EnemyView) enemies.add((EnemyView) object);
        if(object instanceof BulletView) bullets.add((BulletView) object);
    }

    public void remove(GameObject object)
    {
        Node node = getNode(object);

        if(node != null)
        {
            gamePane.getChildren().remove(node);
            if(node instanceof EnemyView)
            {
                enemies.remove(node);
            }
            if(node instanceof BulletView)
            {
                bullets.remove(node);
            }
        }
    }

    /// Returns a node (the view) of a certain GameObject.
    /// Inside gamePane.getChildren() there are only instances of GameObjectView.
    private Node getNode(GameObject object)
    {
        for(Node g : gamePane.getChildren())
        {
            if(((GameObjectView) g).getObject().equals(object))
                return g;
        }
        return null;
    }

    public static Position getMousePosition() {
        return mousePosition;
    }
}