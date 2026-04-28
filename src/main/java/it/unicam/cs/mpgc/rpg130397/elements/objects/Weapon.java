package it.unicam.cs.mpgc.rpg130397.elements.objects;

import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Characteristics;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.WeaponStats;
import it.unicam.cs.mpgc.rpg130397.gamelogic.GameController;
import it.unicam.cs.mpgc.rpg130397.utils.NearestEnemyUpdater;

public class Weapon {
    WeaponStats stats;
    private int level;

    //the fields must not be saved in the json so it has to be declared transient
    private transient long lastAttack;
    private transient NearestEnemyUpdater utils;
    private transient float damage;

    public Weapon(WeaponStats stats, int level, NearestEnemyUpdater utils) {
        this.stats = stats;
        this.level = level;
        this.utils = utils;
    }

    public boolean canAttack()
    {
        //TODO implementare la distanza
        return lastAttack + stats.getCooldown() < System.currentTimeMillis();
    }

    public void attack()
    {
        if(!canAttack()) return;
        switch (stats.getWeaponType())
        {
            case STRENGTH -> meleeAttack();
            case DEXTERITY -> rangedAttack();
            case INTELLIGENCE -> magicAttack();
        }
    }

    //Intelligence weapons attack automatically and target the closest enemy
    private void magicAttack() {
        utils.updateClosestEnemy();
        //se la distanza è troppo grande non attacca
        //istanza il colpo con obiettivo gamecontroller.getclosestenemy()
        lastAttack = System.currentTimeMillis();
    }

    //Dexterity weapons attack towards mouse position
    private void rangedAttack() {
        //istanza il colpo con destinazione puntatore del mouse
        lastAttack = System.currentTimeMillis();
    }

    //Strength weapons attack when the enemy is close enough and no shot is created
    private void meleeAttack() {
        utils.updateClosestEnemy();
        //controlla la distanza melee con le entità a distanza minore del range di attacco dell'arma melee
        lastAttack = System.currentTimeMillis();
    }

    public void updateDamage(Characteristics characteristics)
    {
        int level = characteristics.getCharacteristic(stats.getWeaponType());

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
}
