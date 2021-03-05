package com.quoxsii.locus.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.quoxsii.locus.LocusGame;
import com.quoxsii.locus.entities.Asteroid;
import com.quoxsii.locus.entities.Player;

import java.util.ArrayList;
import java.util.Random;

public class GameScreen implements Screen {
    private static final float MIN_ASTEROID_SPAWN_TIME = 0.5f;
    private static final float MAX_ASTEROID_SPAWN_TIME = 0.8f;

    ShapeRenderer sr;
    LocusGame game;
    Player player;
    Asteroid asteroid;
    Stage stage;
    OrthographicCamera camera;
    Random random;
    ArrayList<Asteroid> asteroids;
    float stateTime;
    float asteroidSpawnTimer;

    public GameScreen(LocusGame game) {
        this.game = game;
        sr = new ShapeRenderer();
        createCamera();
        stage = new Stage(new StretchViewport(LocusGame.SCREEN_WIDTH, LocusGame.SCREEN_HEIGHT));
        initPlayer();
        Gdx.input.setInputProcessor(stage);
        asteroids = new ArrayList<Asteroid>();
        random = new Random();
        asteroidSpawnTimer = random.nextFloat() * (MAX_ASTEROID_SPAWN_TIME - MIN_ASTEROID_SPAWN_TIME) + MIN_ASTEROID_SPAWN_TIME;
    }

    private void createCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, LocusGame.SCREEN_WIDTH, LocusGame.SCREEN_HEIGHT);
        camera.update();
    }

    private void initPlayer() {
        player = new Player();
        stage.addActor(player);
    }

    private void initAsteroid() {
        asteroid = new Asteroid(random.nextInt(Gdx.graphics.getWidth() - Asteroid.ASTEROID_WIDTH));
        stage.addActor(asteroid);
    }

    @Override
    public void show() {
        game.batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.06f, 0.06f, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // asteroid spawn code
        asteroidSpawnTimer -= delta;
        if (asteroidSpawnTimer <= 0) {
            asteroidSpawnTimer = random.nextFloat() * (MAX_ASTEROID_SPAWN_TIME - MIN_ASTEROID_SPAWN_TIME) + MIN_ASTEROID_SPAWN_TIME;
            initAsteroid();
            asteroids.add(asteroid);
        }

        // update asteroids
        ArrayList<Asteroid> asteroidsToRemove = new ArrayList<Asteroid>();
        for (Asteroid asteroid : asteroids) {
            if (asteroid.remove) {
                asteroidsToRemove.add(asteroid);
                asteroid.remove();
            }
        }
        asteroids.removeAll(asteroidsToRemove);

        // check for collision
        for (Asteroid asteroid : asteroids) {
            if (asteroid.getRect().overlaps(player.getRect())) {
                this.dispose();
                game.setScreen(new MenuScreen(game));
            }
        }

        stateTime += delta;

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        stage.draw();
        stage.act();

        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.BLACK);
        sr.rect(player.getRect().x,player.getRect().y,player.getRect().width, player.getRect().height);
        for (Asteroid asteroid : asteroids) {
            sr.rect(asteroid.getRect().x,asteroid.getRect().y,asteroid.getRect().width, asteroid.getRect().height);
        }
        sr.end();

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
        game.dispose();
        stage.dispose();
    }
}
