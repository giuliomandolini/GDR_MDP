package it.unicam.cs.mpgc.rpg130397.elements.objects;

import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Characteristics;
import it.unicam.cs.mpgc.rpg130397.gamelogic.GameController;
import it.unicam.cs.mpgc.rpg130397.utils.NearestEnemyUpdater;

public class Weapon {

    private String name;
    private float baseDamage;
    private float cooldown;
    private float range;
    private float area;
    //weaponType defines the characteristic that uses that weapon. this is needed to avoid
    //creating new classes in the case a new characteristic is inserted. Doing so it is sufficient
    //to write the logic here in a new function instead of creating a new class for each characteristic.
    //The code remains readable, scalable and not duplicated.
    private Characteristics.CharacteristicType weaponType;

    //the fields must not be saved in the json so it has to be declared transient
    private transient long lastAttack;
    private transient NearestEnemyUpdater utils;
    private transient float damage;

    public Weapon(String name, float range, float cooldown, float baseDamage, float area,
                  Characteristics.CharacteristicType weaponType, NearestEnemyUpdater utils) {
        this.weaponType = weaponType;
        this.area = area;
        this.range = range;
        this.cooldown = cooldown;
        this.baseDamage = baseDamage;
        this.name = name;
        this.utils = utils;
    }

    public boolean canAttack()
    {
        //TODO implementare la distanza
        return lastAttack + cooldown < System.currentTimeMillis();
    }

    public void attack()
    {
        if(!canAttack()) return;
        switch (weaponType)
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
        int level = characteristics.getCharacteristic(weaponType);

        //for each 5 points after level 10 the damage increases by 1 baseDamage
        //if the level is less than 10 the damage decreases linearly
        if(level > 10) damage = baseDamage * (level / 5.0f - 1);
        else damage = baseDamage * (level / 10f);
    }

    public Characteristics.CharacteristicType getWeaponType() {
        return weaponType;
    }

    public String getName() {
        return name;
    }
}
