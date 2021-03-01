package com.quoxsii.locus.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.quoxsii.locus.Main;
import com.quoxsii.locus.entities.Asteroid;
import com.quoxsii.locus.tools.RectangleCollision;

import java.util.ArrayList;
import java.util.Random;

public class GameScreen implements Screen {
    //Texture img;
    private static final int ACTOR_WIDTH = 210;
    private static final int ACTOR_HEIGHT = 458;
    private static final float ACTOR_SCALE = 0.25f;
    private static final float ACTOR_ANIMATION_TIME = 0.05f;
    private static final int ACTOR_SPEED_MULTIPLIER = 4;
    private static final float ACTOR_ROTATION_SPEED = 0.7f;

    private static final float MIN_ASTEROID_SPAWN_TIME = 0.5f;
    private static final float MAX_ASTEROID_SPAWN_TIME = 0.8f;

    Animation[] rolls;

    float x = Main.GAME_WIDTH / 2 - ACTOR_WIDTH * (ACTOR_SCALE / 2);
    float y = 10;
    float rotation;
    int speed;
    int roll;
    float stateTime;
    float asteroidSpawnTimer;

    TextureRegion[][] rollSpriteSheet;

    RectangleCollision rectangleCollision;

    Random random;

    Main game;

    ArrayList<Asteroid> asteroids;

    public GameScreen(Main game) {
        this.game = game;

        asteroids = new ArrayList<Asteroid>();

        roll = 0;
        rolls = new Animation[2];

        rollSpriteSheet = TextureRegion.split(new Texture("rocketship_sprite.png"), ACTOR_WIDTH, ACTOR_HEIGHT);
        rolls[roll] = new Animation(ACTOR_ANIMATION_TIME, rollSpriteSheet[0]);

        random = new Random();
        asteroidSpawnTimer = random.nextFloat() * (MAX_ASTEROID_SPAWN_TIME - MIN_ASTEROID_SPAWN_TIME) + MIN_ASTEROID_SPAWN_TIME;

        this.rectangleCollision = new RectangleCollision(x, y, ACTOR_WIDTH * ACTOR_SCALE, ACTOR_HEIGHT * ACTOR_SCALE);
    }

    @Override
    public void show() {
        //img = new Texture("spaceship.png");
        game.batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.04f, 0.06f, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // controls
        if (!Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT)
        || Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.RIGHT)
        || Gdx.input.isKeyPressed(Input.Keys.LEFT) && rotation < 0
        || Gdx.input.isKeyPressed(Input.Keys.RIGHT) && rotation > 0) {
            rotation += rotation > 0 ? -ACTOR_ROTATION_SPEED : ACTOR_ROTATION_SPEED;
            if (rotation < 1 && rotation > 0 || rotation > -1 && rotation < 0) rotation = 0;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT) && rotation >= 0 && rotation < 50) {
            rotation += ACTOR_ROTATION_SPEED;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !Gdx.input.isKeyPressed(Input.Keys.LEFT) && rotation <= 0 && rotation > -50) {
            rotation -= ACTOR_ROTATION_SPEED;
        }

        speed = ACTOR_SPEED_MULTIPLIER * Math.round(Math.abs(rotation));
        x += rotation > 0 ? -(speed * Gdx.graphics.getDeltaTime()) : speed * Gdx.graphics.getDeltaTime();

        rectangleCollision.move(x, y);

        // asteroid spawn code
        asteroidSpawnTimer -= delta;
        if (asteroidSpawnTimer <= 0) {
            asteroidSpawnTimer = random.nextFloat() * (MAX_ASTEROID_SPAWN_TIME - MIN_ASTEROID_SPAWN_TIME) + MIN_ASTEROID_SPAWN_TIME;
            asteroids.add(new Asteroid(random.nextInt(Gdx.graphics.getWidth() - Asteroid.WIDTH)));
        }

        // update asteroids
        ArrayList<Asteroid> asteroidsToRemove = new ArrayList<Asteroid>();
        for (Asteroid asteroid : asteroids) {
            asteroid.update(delta);
            if (asteroid.remove) {
                asteroidsToRemove.add(asteroid);
            }
        }
        asteroids.removeAll(asteroidsToRemove);

        // check for collision
        for (Asteroid asteroid : asteroids) {
            if (asteroid.getRectangleCollision().collidesWith(rectangleCollision)) {
                //this.dispose();
                //game.setScreen(new MenuScreen(game));
            }
        }

        stateTime += delta;

        game.batch.begin();

        for (Asteroid asteroid : asteroids) {
            asteroid.render(game.batch);
        }

        game.batch.draw((TextureRegion) rolls[roll].getKeyFrame(stateTime, true), x, y, ACTOR_WIDTH * (ACTOR_SCALE / 2), ACTOR_HEIGHT * ACTOR_SCALE / 2, ACTOR_WIDTH, ACTOR_HEIGHT, ACTOR_SCALE, ACTOR_SCALE, rotation);

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        //game.batch.dispose();
    }
}
