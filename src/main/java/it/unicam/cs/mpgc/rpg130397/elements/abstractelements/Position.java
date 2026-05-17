package it.unicam.cs.mpgc.rpg130397.elements.abstractelements;

public class Position {
    private float x;
    private float y;

    public Position()
    {
        x = 0;
        y = 0;
    }
    public Position(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void move(float x, float y)
    {
        this.x += x;
        this.y += y;
    }
    public void moveTowards(Position target, float speed)
    {
        float distanceX = target.getX() - x;
        float distanceY = target.getY() - y;

        float totalDistance = (float) Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        if(totalDistance == 0)
            return;

        x += distanceX / totalDistance * speed;
        y += distanceY / totalDistance * speed;
    }

    public float distanceFrom(Position otherPosition)
    {
        return (float) Math.sqrt( Math.pow(x - otherPosition.getX(), 2) + Math.pow(y - otherPosition.getY(), 2));
    }

}
