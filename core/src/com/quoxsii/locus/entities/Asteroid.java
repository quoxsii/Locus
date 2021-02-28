package com.quoxsii.locus.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.quoxsii.locus.tools.RectangleCollision;

import java.util.Random;

public class Asteroid {
    private static final int MIN_SPEED = 200;
    private static final int MAX_SPEED = 260;
    public static final int WIDTH = 65;
    public static final int HEIGHT = 65;
    private Texture texture;

    private Random random;

    float x, y;
    float asteroidSpeed;
    public boolean remove = false;

    RectangleCollision rectangleCollision;

    public Asteroid(float x) {
        this.x = x;
        this.y = Gdx.graphics.getHeight();

        random = new Random();

        if (texture == null) {
            texture = new Texture("asteroid_" + random.nextInt(3) + ".png");
        }

        asteroidSpeed = random.nextFloat() * (MAX_SPEED - MIN_SPEED) + MIN_SPEED;

        this.rectangleCollision = new RectangleCollision(x, y, WIDTH, HEIGHT);
    }

    public void update(float deltaTime) {
        y -= asteroidSpeed * deltaTime;
        if(y < -HEIGHT) {
            remove = true;
        }

        rectangleCollision.move(x, y);
    }

    public void render (SpriteBatch batch) {
        batch.draw(texture, x, y, WIDTH, HEIGHT);
    }

    public RectangleCollision getRectangleCollision() {
        return rectangleCollision;
    }
}
