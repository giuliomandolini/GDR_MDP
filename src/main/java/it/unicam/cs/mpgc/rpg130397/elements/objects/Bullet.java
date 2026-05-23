package it.unicam.cs.mpgc.rpg130397.elements.objects;

import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.BulletStats;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Position;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Entity;
import it.unicam.cs.mpgc.rpg130397.elements.entities.GameObject;
import it.unicam.cs.mpgc.rpg130397.gamelogic.GameData;

/// Class that implements every projectile in the game
public class Bullet extends GameObject {

    private final BulletStats stats;
    //the damage is inherited from the entity that spawns it
    private final transient float damage;
    private final transient float area;
    private final transient float range;
    private final transient float dirX;
    private final transient float dirY;
    private final transient Entity spawner;
    //needed for range to be effective
    private final transient Position spawnPosition;

    static final float LIFESPAN = 7000f;
    private final transient long spawnTime;

    //Used for definition inside the JSON, the damage is set runtime on the Enemy constructor
    public Bullet(BulletStats stats, float damage, Entity spawner, Position target, float range, float area)
    {
        super(stats.getName(), new Position(spawner.getPosition().getX(), spawner.getPosition().getY()));

        this.stats = stats;
        this.spawner = spawner;
        this.damage = damage;
        this.area = area;
        this.range = range;
        spawnPosition = new Position(getPosition().getX(), getPosition().getY());

        //needed to keep the bullet running
        float distanceX = target.getX() - spawnPosition.getX();
        float distanceY = target.getY() - spawnPosition.getY();

        float totalDistance = (float)Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        dirX = distanceX / totalDistance;
        dirY = distanceY / totalDistance;

        //takes in consideration only the past 6 hours (better override of hashcode)
        //(21600000 is the number of milliseconds in 6 hours)
        spawnTime = System.currentTimeMillis();
    }
    ///has to be called on each update
    public void update()
    {
        move();
        checkForLifespanAndRange();
    }

    private void checkForLifespanAndRange() {
        if(System.currentTimeMillis() > spawnTime + LIFESPAN
        || spawnPosition.distanceFrom(getPosition()) > range) GameData.removeBullet(this);
    }

    private void move()
    {
        getPosition().move(dirX * stats.getSpeed(), dirY * stats.getSpeed());
    }

    public Entity getSpawner() {
        return spawner;
    }

    public float getDamage() {
        return damage;
    }

    public float getArea() {
        return area;
    }

    public long getSpawnTime() {
        return spawnTime;
    }

    @Override
    public boolean equals(Object o)
    {
        if(!(o instanceof Bullet)) return false;
        return ((Bullet) o).getSpawnTime() == spawnTime && ((Bullet) o).getSpawner().equals(spawner);
    }

    @Override
    public int hashCode()
    {
        return (int) spawnTime + spawner.hashCode();
    }

}
