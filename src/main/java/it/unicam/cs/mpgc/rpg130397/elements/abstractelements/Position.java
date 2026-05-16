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

    public float distanceFrom(Position otherPosition)
    {
        return (float) Math.sqrt( Math.pow(x - otherPosition.getX(), 2) + Math.pow(y - otherPosition.getY(), 2));
    }

}
