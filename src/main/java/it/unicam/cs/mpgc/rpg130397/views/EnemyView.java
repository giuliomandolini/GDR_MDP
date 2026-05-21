package it.unicam.cs.mpgc.rpg130397.views;

import it.unicam.cs.mpgc.rpg130397.controllers.GameController;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Position;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.gamelogic.GameData;
import it.unicam.cs.mpgc.rpg130397.utils.ScreenToWorldPoint;

public class EnemyView extends GameObjectView {

    private Enemy enemy;

    public EnemyView(Enemy e) {
        super(e);
        enemy = e;
    }

    public void update()
    {
        Position newPos = ScreenToWorldPoint.worldToScreen(enemy.getPosition());
        setLayoutX(newPos.getX());
        setLayoutY(newPos.getY());
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
