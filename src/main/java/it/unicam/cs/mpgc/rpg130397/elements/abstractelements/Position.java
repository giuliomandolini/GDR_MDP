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

    public void Move(float x, float y)
    {
        this.x += x;
        this.y += y;
    }

}
