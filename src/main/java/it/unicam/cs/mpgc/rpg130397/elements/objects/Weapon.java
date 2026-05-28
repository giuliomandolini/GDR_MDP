package it.unicam.cs.mpgc.rpg130397.elements.objects;

import it.unicam.cs.mpgc.rpg130397.controllers.GameController;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Characteristics;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Position;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.WeaponStats;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Player;
import it.unicam.cs.mpgc.rpg130397.gamelogic.CollisionSystem;
import it.unicam.cs.mpgc.rpg130397.gamelogic.CombatSystem;
import it.unicam.cs.mpgc.rpg130397.gamelogic.GameData;
import it.unicam.cs.mpgc.rpg130397.gamelogic.SpawnSystem;
import it.unicam.cs.mpgc.rpg130397.utils.NearestEnemyUpdater;

import java.util.Set;

/// Weapon is the class used by the weapons the player can attack with
public class Weapon {
    //Assigned by json
    private final String name;

    //the fields must not be saved in the json so it has to be declared transient
    private transient WeaponStats stats;
    //the level is given by the last save
    private transient int level;
    private transient long lastAttack;
    //damage is determined runtime by the level and stats.baseDamage
    private transient float damage;

    public Weapon(String name)
    {
        this.name = name;
        level = GameData.getWeaponLevel(name);
        stats = GameData.getWeaponStatMap().get(name);
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

    private boolean canAttack()
    {
        if(lastAttack + stats.getCooldown() > System.currentTimeMillis()) return false;
        switch (stats.getWeaponType())
        {
            case DEXTERITY -> {
                return true;
            }  //le armi a destrezza attaccano sempre
            case INTELLIGENCE -> {
                Enemy closestEnemy = NearestEnemyUpdater.updateAndGetClosestEnemy();
                if(closestEnemy == null) return false;
                Position target = closestEnemy.getPosition();
                return GameData.getPlayer().getPosition().distanceFrom(target) <= stats.getRange();
            }
            case STRENGTH -> {
                Set<Enemy> collisions = CollisionSystem.getPlayerCollisions(Enemy.class);
                return !collisions.isEmpty();
            }
        }
        throw new IllegalStateException("This weapon doesn't have a canAttack condition: " + name + " " + stats.getWeaponType());
    }

    //Intelligence weapons attack automatically and target the closest enemy
    private void intelligenceAttack() {
        //the update of the method occurs once every tens of milliseconds.
        //it can't produce nullPointerException because canAttack() controls if the weapon can't reach the target
        Position target = NearestEnemyUpdater.updateAndGetClosestEnemy().getPosition();
        SpawnSystem.createBullet(stats.getBulletStats(), damage, GameData.getPlayer(), target, stats.getRange(), stats.getArea());
    }

    //Dexterity weapons attack towards mouse position
    private void dexAttack() {
        Position target = GameController.getMousePosition();

        if(target == null)
            target = new Position(0, 0);

        SpawnSystem.createBullet(stats.getBulletStats(), damage, GameData.getPlayer(), target, stats.getRange(), stats.getArea());
    }

    public void strengthAttack()
    {
        Set<Enemy> collisions = CollisionSystem.getPlayerCollisions(Enemy.class);
        Enemy first = (Enemy) collisions.toArray()[0];
        CombatSystem.damage(first, damage);
    }



    //used to update the damage dealt by the weapon in case of a power up of its level or player characteristics
    public void updateDamage(Characteristics characteristics)
    {
        int charLevel = characteristics.getCharacteristicValue(stats.getWeaponType());

        //for each 5 points after level 10 the damage increases by 1 baseDamage
        //if the level is less than 10 the damage decreases linearly
        if(charLevel > 10) damage = stats.getBaseDamage() * (charLevel / 5.0f - 1);
        else damage = stats.getBaseDamage() * (charLevel / 10f);
        //and the damage increases by 20% for each level
        damage *= (charLevel / 5.0f) + 1;

        //the damage also increases by 20% for each level above 1
        damage += damage * 0.2f * (level - 1);
    }

    public WeaponStats getStats() {
        return stats;
    }

    public void setStats(WeaponStats stats) {
        this.stats = stats;
        updateDamage(GameData.getPlayer().getCharacteristics());
    }

    public String getName() {
        return name;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void levelUp()
    {
        level++;
        GameData.saveWeaponLevel(name, level);
        GameController.uiNeedsUpdate();
    }

    //todo togli
    @Override
    public String toString() {
        return name;
    }
}
