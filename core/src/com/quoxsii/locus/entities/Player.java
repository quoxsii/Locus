package com.quoxsii.locus.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.quoxsii.locus.LocusGame;

public class Player extends Actor {
    static final int FRAME_COLS = 5, FRAME_ROWS = 3;
    static final int PLAYER_WIDTH = 128, PLAYER_HEIGHT = 128;
    static final int STARTING_X = LocusGame.SCREEN_WIDTH/2 - PLAYER_WIDTH/2;
    static final int STARTING_Y = 10;
    static final int PLAYER_SPEED = 100;
    static final float MAX_PLAYER_ROTATION_DEGREE = 50.0f;
    static final float INITIAL_ROTATION_DEGREE_AMOUNT = 0.5f;
    static final float ROTATION_ACCELERATION_DEGREE = 0.05f;

    Animation<TextureRegion> flyAnimation;
    Texture flySheet;
    TextureRegion textureRegion;
    Rectangle rect;
    float stateTime;
    int animationFrameRow;
    float x = STARTING_X;
    float rotationDegreeAmount = INITIAL_ROTATION_DEGREE_AMOUNT;
    boolean controllable = true;

    public Player() {
        flySheet = new Texture(Gdx.files.internal("sheets/rocket-sheet.png"));
        this.setAnimationFrameRow(0);
        this.setPosition(STARTING_X, STARTING_Y);
        this.setSize(PLAYER_WIDTH, PLAYER_HEIGHT);
        this.rect = new Rectangle(STARTING_X, STARTING_Y, PLAYER_WIDTH, PLAYER_HEIGHT);
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setAnimationFrameRow(int row) {
        if (row > FRAME_ROWS || row < 0) {
            throw new IllegalArgumentException("value of 'row' in setAnimationFrameRow(int row) is outside the range of frame rows");
        }

        TextureRegion[][] tmp = TextureRegion.split(flySheet,
                flySheet.getWidth() / FRAME_COLS,
                flySheet.getHeight() / FRAME_ROWS);

        TextureRegion[] flyFrames = new TextureRegion[FRAME_COLS];
        int index = 0;
        for (int i = 0; i < FRAME_COLS; i++) {
            flyFrames[index++] = tmp[row][i];
        }

        flyAnimation = new Animation<TextureRegion>(0.025f, flyFrames);
        stateTime = 0f;
        textureRegion = flyAnimation.getKeyFrame(0);
        textureRegion.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        animationFrameRow = row;
    }

    private void turnLeft() {
        if (getRotation() < MAX_PLAYER_ROTATION_DEGREE) {
            if (getRotation() + rotationDegreeAmount > MAX_PLAYER_ROTATION_DEGREE) {
                setRotation(MAX_PLAYER_ROTATION_DEGREE);
            } else {
                rotateBy(rotationDegreeAmount);
                rotationDegreeAmount += ROTATION_ACCELERATION_DEGREE;
            }

            if (animationFrameRow != 1) {
                setAnimationFrameRow(1);
            }
        }
    }

    private void turnRight() {
        if (getRotation() > -MAX_PLAYER_ROTATION_DEGREE) {
            if (getRotation() - rotationDegreeAmount < -MAX_PLAYER_ROTATION_DEGREE) {
                setRotation(-MAX_PLAYER_ROTATION_DEGREE);
            } else {
                rotateBy(-rotationDegreeAmount);
                rotationDegreeAmount += ROTATION_ACCELERATION_DEGREE;
            }

            if (animationFrameRow != 2) {
                setAnimationFrameRow(2);
            }
        }
    }

    private void move() {
        if (!Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT) && getRotation() != 0.0f
                || Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.RIGHT)
                || Gdx.input.isKeyPressed(Input.Keys.LEFT) && getRotation() < 0.0f
                || Gdx.input.isKeyPressed(Input.Keys.RIGHT) && getRotation() > 0.0f) {
            if (controllable) {
                rotationDegreeAmount = INITIAL_ROTATION_DEGREE_AMOUNT;
            }
            controllable = false;
        } else {
            controllable = true;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && getRotation() > 0.0f && rotationDegreeAmount != 0.0f
                || Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && getRotation() < 0.0f && rotationDegreeAmount != 0.0f) {
            rotationDegreeAmount = 0.0f;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && controllable) {
            turnLeft();
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && controllable) {
            turnRight();
        }

        if (!controllable) {
            if (getRotation() > 0.0f) {
                if (getRotation() - rotationDegreeAmount < 0.0f) {
                    setRotation(0.0f);
                    rotationDegreeAmount = INITIAL_ROTATION_DEGREE_AMOUNT;
                } else {
                    turnRight();
                }
            }
            else if (getRotation() < 0.0f) {
                if (getRotation() + rotationDegreeAmount > 0.0f) {
                    setRotation(0.0f);
                    rotationDegreeAmount = INITIAL_ROTATION_DEGREE_AMOUNT;
                } else {
                    turnLeft();
                }
            }
        }

        x -= PLAYER_SPEED * (getRotation() / 360);
        setX(x);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta;
        textureRegion = flyAnimation.getKeyFrame(stateTime,true);
        move();
        rect.setPosition(getX(), getY());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(textureRegion, getX(), getY(), getWidth()/2, getHeight()/2, getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
}
