package it.unicam.cs.mpgc.rpg130397.elements.objects;

public class Bullet {

    private float speed;
    private float damage;
    static final float LIFESPAN = 5f;
    private long spawnTime;
    //TODO
    //private Transform target?

    public Bullet(float speed, float damage) {
        this.speed = speed;
        this.damage = damage;
        spawnTime = System.currentTimeMillis();
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

}
