package it.unicam.cs.mpgc.rpg130397.controllers;

import it.unicam.cs.mpgc.rpg130397.Main;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Characteristics;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.EntityStats;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Position;
import it.unicam.cs.mpgc.rpg130397.elements.entities.GameObject;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Player;
import it.unicam.cs.mpgc.rpg130397.elements.objects.Weapon;
import it.unicam.cs.mpgc.rpg130397.gamelogic.*;
import it.unicam.cs.mpgc.rpg130397.utils.SceneManager;
import it.unicam.cs.mpgc.rpg130397.utils.ScreenToWorldPoint;
import it.unicam.cs.mpgc.rpg130397.views.GameObjectView;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/// Controller of the game scene. Its responsibility regards the graphic interface for the scene.
/// - It contains the timer and so all the root calls of the updates
/// - Manages the ui for the scene (syncs the views with the models and manages javafx controls).
/// - The initialize() method needs to call the start of the game in each
public class GameController {
    @FXML
    private Rectangle healthBar;
    @FXML
    private AnchorPane gamePane;
    @FXML
    private Button playAgain;

    @FXML
    private Label strengthLevel;
    @FXML
    private Label dexterityLevel;
    @FXML
    private Label intelligenceLevel;
    @FXML
    private Label strengthWeapon;
    @FXML
    private Label dexterityWeapon;
    @FXML
    private Label intelligenceWeapon;
    @FXML
    private Label strengthWeaponLevel;
    @FXML
    private Label dexterityWeaponLevel;
    @FXML
    private Label intelligenceWeaponLevel;

    private static List<Label> weaponNames;
    private static List<Label> weaponLevels;
    private static List<Label> characteristicLevels;

    private static Map<Class<? extends GameObject>, List<GameObjectView<? extends GameObject>>> views;

    private AnimationTimer timer;
    private static boolean lost;

    @FXML
    public void initialize() throws FileNotFoundException{
        views = new HashMap<>();
        lost = false;

        GameManager.setupGameLogic();
        createPlayer();
        //needs the player to be created before to bind health property to the health bar
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

    //instantiates the player and sets up correct scale and position
    private void createPlayer() throws FileNotFoundException {
        Player playerModel = new Player();

        GameObjectView<Player> p = new GameObjectView<>(playerModel);
        p.setScaleX(0.8);
        p.setScaleY(0.8);
        addView(p);
        //puts the player at the center of the screen
        p.setLayoutX(Main.SCREEN_WIDTH / 2f);
        p.setLayoutY(Main.SCREEN_HEIGHT / 2f);
    }

    //assignment of properties and general setup for the ui: labels, graphics and buttons.
    private void setupUi()
    {
        //the button has to be removed or else it would count as a child of gamePane and getNode would iterate also through it
        gamePane.getChildren().remove(playAgain);

        Player p = getViews(Player.class).getFirst().getObject();
        healthBar.widthProperty().bind(p.getHealthProperty().divide(p.getStats().get(EntityStats.StatType.MAX_HEALTH) / 155));

        gamePane.setFocusTraversable(true);

        weaponNames = List.of(strengthWeapon, dexterityWeapon, intelligenceWeapon);
        weaponLevels = List.of(strengthWeaponLevel, dexterityWeaponLevel, intelligenceWeaponLevel);
        characteristicLevels = List.of(strengthLevel, dexterityLevel, intelligenceLevel);

    }

    //This method only sets up the logic because the events are relative to javafx so the setup has to be in the contorller,
    //but all the input logic is in the InputManager, keeping the responsabilities clear
    private void setupInput()
    {
        gamePane.setOnMouseMoved(event -> InputManager.getMousePosition().setPosition(ScreenToWorldPoint.screenToWorld(new Position((float) event.getX(), (float) event.getY()))));
        gamePane.setOnKeyPressed(keyEvent -> InputManager.keyPressed(keyEvent.getCode()));
        gamePane.setOnKeyReleased(keyEvent -> InputManager.keyReleased(keyEvent.getCode()));
    }

    //Synchronizes models with views and controls game (shares the update to all the other objects by the GameManager)
    private void update()
    {
        if(lost) {
            manageLoss();
            return;
        }
        //views update
        createDeleteViews();
        updateViewPositions();

        //logic update
        GameManager.update();

        updateUi();
    }

    private void updateViewPositions() {
        for(List<GameObjectView<?>> l : views.values())
        {
            for(GameObjectView<?> v : l)
            {
                v.update();
            }
        }
    }

    private void createDeleteViews()
    {
        for(GameObject g : GameData.getElementsToSpawn())
        {
            addView(g);
        }
        for(GameObject g : GameData.getElementsToDespawn())
        {
            removeView(g);
        }
    }

    //needed as a bridge from static classes and logic and the GameContoroller class.
    public static void lose()
    {
        lost = true;
    }

    private void manageLoss()
    {
        timer.stop();
        removeView(GameData.getPlayer());
        gamePane.getChildren().add(playAgain);
    }

    public static void updateUi()
    {
        //Could have used properties, but there would have been many and it would scale
        //badly in case of new weapons, characteristics or inventory size increase.
        int i = 0;
        for(Characteristics.CharacteristicType t : Characteristics.CharacteristicType.values())
        {
            Weapon current = GameData.getPlayer().getInventory().get(t);
            weaponNames.get(i).setText(current != null ? current.getName() : "No weapon");
            weaponLevels.get(i).setText(current != null ? String.valueOf(current.getLevel()) : "");
            characteristicLevels.get(i).setText(String.valueOf(GameData.getPlayer().getCharacteristics().getCharacteristicValue(t)));
            i++;
        }

    }

    @FXML
    private void playAgain() throws IOException {
        SceneManager.loadScene("game");
    }

    //Returns a GameObjectView (the view) of a certain GameObject.
    //Inside gamePane.getChildren() there are only instances of GameObjectView.
    private GameObjectView<?> getNode(GameObject object)
    {
        for(Node g : gamePane.getChildren())
        {
            if(((GameObjectView<?>) g).getObject().equals(object))
                return (GameObjectView<?>) g;
        }
        return null;
    }

    //it is better to use linked lists instead of array lists because a there are a lot of additions and remotions
    private <T extends GameObject> void addView(T object)
    {
        Class<? extends GameObject> type =  object.getClass();
        if(views.get(type) == null) views.put(type, new LinkedList<>());
        GameObjectView<T> newView = new GameObjectView<>(object);
        views.get(type).add(newView);
        gamePane.getChildren().add(newView);
    }
    private <T extends GameObject> void addView(GameObjectView<T> object)
    {
        Class<? extends GameObject> type =  object.getObject().getClass();
        if(views.get(type) == null) views.put(type, new LinkedList<>());
        views.get(type).add(object);
        gamePane.getChildren().add(object);
    }

    private <T extends GameObject> void removeView(T object)
    {
        Class<? extends GameObject> type =  object.getClass();
        if(views.get(type) == null) return;

        views.get(type).remove(getNode(object));
        gamePane.getChildren().remove(getNode(object));
    }

    /// Returns the view of the Player GameObject.
    /// @return the player view as a GameObjectView.
    public static GameObjectView<Player> getPlayer()
    {
        List<GameObjectView<Player>> players = getViews(Player.class);
        if(players.isEmpty()) throw new IllegalStateException("There is no player in the scene");
        if(players.size() > 1) throw new IllegalStateException("There are too many players in the scene");
        return players.getFirst();
    }

    //only way to cast is to cast before List<GameObjectView<?>> in a List<?> and then into List<GameObjectView<T>>
    //or else it would be necessary to use a cast on each call of getViews because the return type GameObjectView<?> would have
    //a generic GameObject and not the type T used by the GameObjectView
    /// Gets all the views of a determined class.
    /// @return the List of the GameObjectViews of the determined class, or an empty list if there isn't any.
    /// @param type the class of the determined return type
    @SuppressWarnings("unchecked")
    public static <T extends GameObject> List<GameObjectView<T>> getViews(Class<T> type)
    {
        List<GameObjectView<T>> temp = (List<GameObjectView<T>>) ((List<?>) views.get(type));
        if(temp == null) return new LinkedList<>();
        return temp;
    }

    public static List<GameObjectView<? extends GameObject>> getAllViews()
    {
        return views.values().stream()
                .flatMap(Collection::stream)
                .toList();
    }

}