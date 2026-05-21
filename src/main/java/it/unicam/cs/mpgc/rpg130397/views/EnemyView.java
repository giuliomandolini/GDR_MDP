package it.unicam.cs.mpgc.rpg130397.views;

import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.gamelogic.GameData;

public class EnemyView extends GameObjectView {

    private Enemy enemy;

    public EnemyView(Enemy e) {
        super(e);
        enemy = e;
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

        return ((EnemyView) o).getObject().equals(enemy);
    }

    @Override
    public int hashCode()
    {
        return enemy.hashCode();
    }

    public Enemy getObject() {
        return enemy;
    }
}
