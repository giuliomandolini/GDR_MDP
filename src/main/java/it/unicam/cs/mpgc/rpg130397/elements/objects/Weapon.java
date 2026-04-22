package it.unicam.cs.mpgc.rpg130397.elements.objects;

import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Characteristics;
import it.unicam.cs.mpgc.rpg130397.gamelogic.GameController;

public class Weapon {

    private String name;
    private float baseDamage;
    private float cooldown;
    private float range;
    private float area;
    private Characteristics.CharacteristicType weaponType;

    //i seguenti campi non devono essere presenti nel json quindi vanno dichiarati transient
    private transient long lastAttack;
    private transient GameController gameController;
    private transient float damage;

    public String getName() {
        return name;
    }

    public Weapon(String name, float range, float cooldown, float baseDamage, float area,
                  Characteristics.CharacteristicType weaponType, GameController gameController) {
        this.weaponType = weaponType;
        this.area = area;
        this.range = range;
        this.cooldown = cooldown;
        this.baseDamage = baseDamage;
        this.name = name;
        this.gameController = gameController;
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
        gameController.updateClosestEnemy();
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
        gameController.updateClosestEnemy();
        //controlla la distanza melee con le entità a distanza minore del range di attacco dell'arma melee
        lastAttack = System.currentTimeMillis();
    }

    public void updateDamage(Characteristics characteristics)
    {
        int level = characteristics.getCharacteristic(weaponType);
        //for each 5 points after level 10 the damage increases by 1 baseDamage
        if(level >= 10) damage = baseDamage * (level / 5.0f - 1);
        //if the level is less than 10 the damage decreases linearly
        else damage = baseDamage * (level / 10f);
    }

    public Characteristics.CharacteristicType getWeaponType() {
        return weaponType;
    }

    //TODO aumentare il danno dell'arma in base alla caratteristica del giocatore
}
