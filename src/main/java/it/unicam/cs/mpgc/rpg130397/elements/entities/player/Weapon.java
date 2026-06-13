package it.unicam.cs.mpgc.rpg130397.elements.entities.player;

import it.unicam.cs.mpgc.rpg130397.controllers.GameController;
import it.unicam.cs.mpgc.rpg130397.gamelogic.Position;
import it.unicam.cs.mpgc.rpg130397.elements.stats.WeaponStats;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.gamelogic.*;
import it.unicam.cs.mpgc.rpg130397.utils.NearestEnemyUpdater;

import java.util.Set;

/// Weapon is the class used by the weapons the player can attack with. <br>
/// The player can have a maximum of 3 weapons, and they have a set of stats, a name and runtime information
/// as the last time it has attacked or the damage it does, relative to the relative actual characteristic of the player,
/// not only to the base damage of the stats.
public class Weapon {
    //Assigned by json
    private final String name;

    //the fields must not be saved in the json so it has to be declared transient
    private transient WeaponStats stats;
    //the level is given by the last save
    private transient int level;
    private transient long lastAttack;

    public Weapon(String name)
    {
        this.name = name;
        level = GameData.getWeaponLevel(name);
        //the stats have to be a copy of the original, because if the weapon gets changed and its stats get updated,
        //the calculations of the damage and cooldown by weaponLevel and characteristicLevel have to be done on the original stats,
        //not the previous that the weapon had before changing.
        stats = new WeaponStats(GameData.getWeaponStatMap().get(name));
    }

    public void attack()
    {
        if(!canAttack()) return;

        switch (stats.getWeaponType())
        {
            case DEXTERITY -> dexAttack();
            case INTELLIGENCE -> intelligenceAttack();
            case STRENGTH -> strengthAttack();
        }
        lastAttack = System.currentTimeMillis();
    }

    //returns if the weapon can attack based on its characteristic: each of them has
    //determined conditions under witch they can attack.
    private boolean canAttack()
    {
        if(lastAttack + stats.getCooldown() > System.currentTimeMillis()) return false;
        switch (stats.getWeaponType())
        {
            case DEXTERITY -> {
                return true;
            }  //dexterity weapons always attack
            case INTELLIGENCE -> {
                Enemy closestEnemy = NearestEnemyUpdater.updateAndGetClosestEnemy();
                if(closestEnemy == null) return false;
                Position target = closestEnemy.getPosition();
                return GameData.getPlayer().getPosition().distanceFrom(target) <= stats.getRange();
            }   //intelligence weapon attack only if there is at least one enemy in range of the weapon
            case STRENGTH -> {
                Set<Enemy> collisions = CollisionSystem.getPlayerCollisions(Enemy.class);
                return !collisions.isEmpty();
            }   //strength weapons attack if there is an enemy colliding.
        }
        throw new IllegalStateException("This weapon doesn't have a canAttack condition: " + name + " " + stats.getWeaponType());
    }

    //Intelligence weapons attack automatically and target the closest enemy
    private void intelligenceAttack() {
        //the update of the method occurs once every tens of milliseconds.
        //it can't produce nullPointerException because canAttack() controls if the weapon can't reach the target
        Position target = NearestEnemyUpdater.updateAndGetClosestEnemy().getPosition();
        SpawnSystem.createBullet(stats.getBulletStats(), stats.getDamage(), GameData.getPlayer(), target, stats.getRange(), stats.getArea());
    }

    //Dexterity weapons attack towards mouse position
    private void dexAttack() {
        Position target = InputManager.getMousePosition();
        Position playerPosition = GameData.getPlayer().getPosition();

        //if there is no mouse position or the player is at the same position as the target, the weapon shoots up
        if(target == null || target.equals(playerPosition))
        {
            target = new Position(playerPosition.getX(), playerPosition.getY() - 1);
        }

        SpawnSystem.createBullet(stats.getBulletStats(), stats.getDamage(), GameData.getPlayer(), target, stats.getRange(), stats.getArea());
    }

    public void strengthAttack()
    {
        Set<Enemy> collisions = CollisionSystem.getPlayerCollisions(Enemy.class);
        Enemy first = (Enemy) collisions.toArray()[0];
        CombatSystem.damage(first, stats.getDamage());
    }

    //used to update the damage dealt by the weapon in case of a power up of its level or player characteristics
    public void updateStats()
    {
        WeaponStats originalStats = GameData.getWeaponStatMap().get(name);

        int charLevel = GameData.getPlayer().getCharacteristics().getCharacteristicValue(stats.getWeaponType());
        //the damage increases by 10% for each characteristic level above 10
        float damage = originalStats.getDamage() * (charLevel / 10.0f);
        //the damage also increases by 5% for each weapon level above 0
        damage *= (1 + 0.05f * (level));
        stats.setDamage(damage);

        //the cooldown decreases by 5% per char level above 10
        float cooldown = originalStats.getCooldown() * (1 - 0.05f * (charLevel - 10));
        //the weapon must have a minimum cooldown
        if(cooldown < 20) cooldown = 20;
        stats.setCooldown((long) (cooldown));
    }

    public WeaponStats getStats() {
        return stats;
    }

    //used at the start to assign the stats and the level at the weapons in the inventory.
    //doesn't need to update ui and save level, because if it doesn't change it remains the same.
    public void setStatsAndLevel(WeaponStats stats, int level) {
        this.stats = new WeaponStats(stats);
        this.level = level;
        updateStats();
    }

    public String getName() {
        return name;
    }

    public void setLevel(int level)
    {
        this.level = level;
        GameData.saveWeaponLevel(name, level);
        updateStats();
        GameController.updateUi();
    }

    public int getLevel() {
        return level;
    }

}
