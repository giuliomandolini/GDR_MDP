package it.unicam.cs.mpgc.rpg130397.elements.objects;

import it.unicam.cs.mpgc.rpg130397.elements.entities.GameObject;

public class Bullet extends GameObject {

    private float speed;
    private float damage;

    static transient final float LIFESPAN = 5f;
    private transient long spawnTime;
    //TODO
    //private Transform target?

    //Used for definition inside the JSON, the damage is set runtime on the Enemy constructor
    public Bullet(String name, float speed)
    {
        super(name);
        this.speed = speed;
        //TODO on spawn         spawnTime = System.currentTimeMillis();
    }
    ///has to be called on each update
    public void update()
    {
        move();
        checkForCollision();
        checkForLifespan();
    }

    private void checkForCollision() {
        //TODO controlla le collisioni con il giocatore
    }

    private void checkForLifespan() {
        //TODO
        if(System.currentTimeMillis() > spawnTime + LIFESPAN) return; //distruggi il proiettile
    }

    private void move()
    {
        //TODO
    }

    public void setDamage(float damage)
    {
        this.damage = damage;
    }

}
