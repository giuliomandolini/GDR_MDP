package it.unicam.cs.mpgc.rpg130397.views;

import it.unicam.cs.mpgc.rpg130397.elements.objects.Bullet;

public class BulletView extends GameObjectView {

    Bullet bullet;

    public BulletView(Bullet b) {
        bullet = b;
        super(b);
    }

    public void update()
    {
        setLayoutX(bullet.getPosition().getX());
        setLayoutY(bullet.getPosition().getY());
    }

    @Override
    public boolean equals(Object o)
    {
        if(!(o instanceof BulletView)) return false;
        return ((BulletView) o).getObject() == bullet;
    }

    @Override
    public int hashCode()
    {
        return bullet.hashCode();
    }

    @Override
    public String toString() {
        return getObject().getName();
    }

    public Bullet getObject() {
        return bullet;
    }
}
