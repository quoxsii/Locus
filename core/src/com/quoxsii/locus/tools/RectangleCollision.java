package com.quoxsii.locus.tools;

public class RectangleCollision {
    float x, y;
    float width, height;

    public RectangleCollision (float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void move (float x, float y) {
        this.x = x;
        this.y = y;
    }

    public boolean collidesWith (RectangleCollision rectangleCollision) {
        return x < rectangleCollision.x + rectangleCollision.width
                && y < rectangleCollision.y + rectangleCollision.height
                && x + width > rectangleCollision.x
                && y + height > rectangleCollision.y;
    }
}
