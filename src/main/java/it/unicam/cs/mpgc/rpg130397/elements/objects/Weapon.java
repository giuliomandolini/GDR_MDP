package it.unicam.cs.mpgc.rpg130397.elements.objects;

import it.unicam.cs.mpgc.rpg130397.controllers.GameController;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Characteristics;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Position;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.WeaponStats;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.gamelogic.GameData;
import it.unicam.cs.mpgc.rpg130397.utils.NearestEnemyUpdater;

/// Weapon is the class used by the weapons the player can attack with
public class Weapon {
    //Assigned by json
    private String name;
    private int level;

    //the fields must not be saved in the json so it has to be declared transient
    private transient WeaponStats stats;
    private transient long lastAttack;
    private transient float damage;

    public void attack()
    {
        Characteristics.CharacteristicType weaponType = stats.getWeaponType();
        //strength weapons don't need an update: they attack when an enemy collides (see CombatSystem)
        if(!canAttack() || weaponType == Characteristics.CharacteristicType.STRENGTH) return;

        switch (weaponType)
        {
            case DEXTERITY -> dexAttack();
            case INTELLIGENCE -> magicAttack();
        }
        lastAttack = System.currentTimeMillis();
    }

    private boolean canAttack()
    {
        return lastAttack + stats.getCooldown() < System.currentTimeMillis();
    }

    //Intelligence weapons attack automatically and target the closest enemy
    private void magicAttack() {
        Enemy closestEnemy = NearestEnemyUpdater.updateAndGetClosestEnemy();
        if(closestEnemy == null) return;
        Position target = closestEnemy.getPosition();
        if(target == null || GameData.getPlayer().getPosition().distanceFrom(target) > stats.getRange()) return;

        createBullet(target);
    }

    //Dexterity weapons attack towards mouse position
    private void dexAttack() {
        Position target = GameController.getMousePosition();

        if(target == null)
            target = new Position(0, 0);

        createBullet(target);
    }

    public boolean meleeAttack()
    {
        boolean res = canAttack();
        if(res)
            lastAttack = System.currentTimeMillis();
        return res;
    }

    private void createBullet(Position target)
    {
        Bullet b = new Bullet(stats.getBulletStats(), damage, GameData.getPlayer(), target, stats.getRange(), stats.getArea());
        GameData.addBullet(b);
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

    public float getDamage() {
        return damage;
    }
}
