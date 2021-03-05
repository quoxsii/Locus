package com.quoxsii.locus.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.Random;

public class Asteroid extends Actor {
    static final int FRAME_COLS = 1, FRAME_ROWS = 3;
    static final int ASTEROID_MIN_SPEED = 200, ASTEROID_MAX_SPEED = 300;
    public static final int ASTEROID_WIDTH = 64, ASTEROID_HEIGHT = 64;

    Texture textureSheet;
    TextureRegion textureRegion;
    Random random;
    Rectangle rect;
    float x, y;
    float asteroidSpeed;
    public boolean remove = false;
    float stateTime;

    public Asteroid(float x) {
        textureSheet = new Texture(Gdx.files.internal("sheets/asteroids-sheet.png"));
        this.x = x;
        y = Gdx.graphics.getHeight();

        random = new Random();
        setTextureSheetFrameRow(random.nextInt(3));
        asteroidSpeed = random.nextFloat() * (ASTEROID_MAX_SPEED - ASTEROID_MIN_SPEED) + ASTEROID_MIN_SPEED;

        this.setPosition(x, y);
        this.setSize(ASTEROID_WIDTH, ASTEROID_HEIGHT);
        this.rect = new Rectangle(x, y, ASTEROID_WIDTH, ASTEROID_HEIGHT);
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setTextureSheetFrameRow(int row) {
        if (row > FRAME_ROWS || row < 0) {
            throw new IllegalArgumentException("value of 'row' in setAnimationFrameRow(int row) is outside the range of frame rows");
        }

        TextureRegion[][] tmp = TextureRegion.split(textureSheet,
                textureSheet.getWidth() / FRAME_COLS,
                textureSheet.getHeight() / FRAME_ROWS);

        stateTime = 0f;
        textureRegion = tmp[row][0];
        textureRegion.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta;

        y -= asteroidSpeed * delta;

        if(y < -ASTEROID_HEIGHT) {
            remove = true;
        }

        this.setY(y);
        rect.setPosition(x, y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(textureRegion, x, y, ASTEROID_WIDTH, ASTEROID_HEIGHT);
    }
}
