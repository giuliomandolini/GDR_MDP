package it.unicam.cs.mpgc.rpg130397.elements.objects;

import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Characteristics;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Position;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.WeaponStats;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.gamelogic.GameData;
import it.unicam.cs.mpgc.rpg130397.utils.NearestEnemyUpdater;

public class Weapon {
    private String name;
    private int level;

    //the fields must not be saved in the json so it has to be declared transient
    private transient WeaponStats stats;
    private transient long lastAttack;
    private transient float damage;


    public boolean canAttack()
    {
        return lastAttack + stats.getCooldown() < System.currentTimeMillis();
    }

    public void attack()
    {
        if(!canAttack())
        {
            System.out.println(name + " cooldown " + lastAttack % 1000000 + ", " + stats.getCooldown() + ", " + System.currentTimeMillis() % 1000000);
            return;
        }
        System.out.println("WEAPON ATTACK " + name );
        switch (stats.getWeaponType())
        {
            case STRENGTH -> meleeAttack();
            case DEXTERITY -> rangedAttack();
            case INTELLIGENCE -> magicAttack();
        }
    }

    //Intelligence weapons attack automatically and target the closest enemy
    private void magicAttack() {
        System.out.println("Weapon " + getName() + " attacking! " + NearestEnemyUpdater.updateAndGetClosestEnemy());

        Enemy closestEnemy = NearestEnemyUpdater.updateAndGetClosestEnemy();
        if(closestEnemy == null) return;
        System.out.println("target = " + closestEnemy.getName() + ", at range " + GameData.getPlayer().getPosition().distanceFrom(closestEnemy.getPosition()) + " can attack at "  + stats.getRange());
        Position target = closestEnemy.getPosition();
        if(target == null || GameData.getPlayer().getPosition().distanceFrom(target) > stats.getRange()) return;

        Bullet b = new Bullet(stats.getBulletStats(), damage, GameData.getPlayer(), target);
        GameData.addBullet(b);

        lastAttack = System.currentTimeMillis();
    }

    //Dexterity weapons attack towards mouse position
    private void rangedAttack() {
        //TODO istanza il colpo con destinazione puntatore del mouse
        lastAttack = System.currentTimeMillis();
    }

    //Strength weapons attack when the enemy is close enough and no shot is created
    private void meleeAttack() {
        //utils.updateClosestEnemy();
        //TODO controlla la distanza melee con le entità a distanza minore del range di attacco dell'arma melee
        lastAttack = System.currentTimeMillis();
    }

    public void updateDamage(Characteristics characteristics)
    {
        int level = characteristics.getCharacteristicValue(stats.getWeaponType());

        //for each 5 points after level 10 the damage increases by 1 baseDamage
        //if the level is less than 10 the damage decreases linearly
        if(level > 10) damage = stats.getBaseDamage() * (level / 5.0f - 1);
        else damage = stats.getBaseDamage() * (level / 10f);
        //and the damage increases by 20% for each level
        damage *= (level / 5.0f) + 1;
    }

    public WeaponStats getStats() {
        return stats;
    }

    public void setStats(WeaponStats stats) {
        this.stats = stats;
    }

    public String getName() {
        return name;
    }
}
