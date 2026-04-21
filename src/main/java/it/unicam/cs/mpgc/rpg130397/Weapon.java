package it.unicam.cs.mpgc.rpg130397;

public class Weapon {

    private String name;
    private float damage;
    private float cooldown;
    private long lastAttack;
    private float range;
    private float area;
    private Characteristics.CharacteristicType weaponType;
    private GameController gameController;

    public String getName() {
        return name;
    }

    public Weapon(String name, float range, float cooldown, float damage, float area,
                  Characteristics.CharacteristicType weaponType, GameController gameController) {
        this.weaponType = weaponType;
        this.area = area;
        this.range = range;
        this.lastAttack = lastAttack;
        this.cooldown = cooldown;
        this.damage = damage;
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
            case STRENGHT -> meleeAttack();
            case DEXTERITY -> rangedAttack();
            case INTELLIGENCE -> magicAttack();
        }
    }

    private void magicAttack() {
        gameController.updateClosestEnemy();
        //se la distanza è troppo grande non attacca
        //istanzia il colpo con obiettivo gamecontroller.getclosestenemy()
        lastAttack = System.currentTimeMillis();
    }

    private void rangedAttack() {
        //istanzia il colpo con destinazione puntatore del mouse
        lastAttack = System.currentTimeMillis();
    }

    private void meleeAttack() {
        gameController.updateClosestEnemy();
        //controlla la distanza melee con le entità a distanza minore del range di attacco dell'arma melee
        lastAttack = System.currentTimeMillis();
    }

}
