package it.unicam.cs.mpgc.rpg130397.views;

import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.elements.objects.Bullet;

public class EnemyView extends GameObjectView {

    Enemy enemy;

    public EnemyView(Enemy e) {
        enemy = e;
        super(e);
    }

    public void update()
    {
        setLayoutX(enemy.getPosition().getX());
        setLayoutY(enemy.getPosition().getY());
    }

    @Override
    public boolean equals(Object o)
    {
        if(!(o instanceof EnemyView)) return false;
        return ((EnemyView) o).getEnemy() == enemy;
    }

    @Override
    public int hashCode()
    {
        return enemy.hashCode();
    }

    public Enemy getEnemy() {
        return enemy;
    }
}
