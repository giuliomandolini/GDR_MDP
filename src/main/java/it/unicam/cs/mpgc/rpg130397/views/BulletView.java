package it.unicam.cs.mpgc.rpg130397.views;

import it.unicam.cs.mpgc.rpg130397.controllers.GameController;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Position;
import it.unicam.cs.mpgc.rpg130397.elements.objects.Bullet;
import it.unicam.cs.mpgc.rpg130397.gamelogic.GameData;
import it.unicam.cs.mpgc.rpg130397.utils.ScreenToWorldPoint;

public class BulletView extends GameObjectView {

    Bullet bullet;

    public BulletView(Bullet b) {
        bullet = b;
        super(b);
    }

    public void update()
    {
        Position newPos = ScreenToWorldPoint.worldToScreen(bullet.getPosition());
        setLayoutX(newPos.getX());
        setLayoutY(newPos.getY());
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
