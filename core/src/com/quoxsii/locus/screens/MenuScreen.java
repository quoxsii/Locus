package com.quoxsii.locus.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.quoxsii.locus.LocusGame;

public class MenuScreen implements Screen {
    private static final int BUTTON_WIDTH = 150;
    private static final int BUTTON_HEIGHT = 50;

    LocusGame game;
    Group group;
    Stage stage;
    OrthographicCamera camera;
    Rectangle playButtonRect, exitButtonRect;

    public MenuScreen(LocusGame game) {
        this.game = game;
        createCamera();
        stage = new Stage(new StretchViewport(LocusGame.SCREEN_WIDTH, LocusGame.SCREEN_HEIGHT));
        group = new Group();
        initButtons();
    }

    private void initButtons() {
        int buttonXCenter = LocusGame.SCREEN_WIDTH / 2 - BUTTON_WIDTH / 2;
        int playButtonYCenter = LocusGame.SCREEN_HEIGHT / 2 - BUTTON_HEIGHT / 2 + 30;
        int exitButtonYCenter = LocusGame.SCREEN_HEIGHT / 2 - BUTTON_HEIGHT / 2 - 30;

        Actor playButton = new Image(new Texture("button_play.png"));
        playButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        playButton.setName("play_button");
        playButton.setPosition(buttonXCenter, playButtonYCenter);
        playButtonRect = new Rectangle(playButton.getX(), playButton.getY(), playButton.getWidth(), playButton.getHeight());

        Actor exitButton = new Image(new Texture("button_exit.png"));
        exitButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        exitButton.setName("exit_button");
        exitButton.setPosition(buttonXCenter, exitButtonYCenter);
        exitButtonRect = new Rectangle(exitButton.getX(), exitButton.getY(), exitButton.getWidth(), exitButton.getHeight());

        group.addActor(playButton);
        group.addActor(exitButton);
        stage.addActor(group);
    }

    private void createCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, LocusGame.SCREEN_WIDTH, LocusGame.SCREEN_HEIGHT);
        camera.update();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.04f, 0.06f, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.justTouched()) {
            if (playButtonRect.contains(Gdx.input.getX(), LocusGame.SCREEN_HEIGHT - Gdx.input.getY())) {
                this.dispose();
                game.setScreen(new GameScreen(game));
            } else if (exitButtonRect.contains(Gdx.input.getX(), LocusGame.SCREEN_HEIGHT - Gdx.input.getY())) {
                this.dispose();
                Gdx.app.exit();
            }
        }

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        stage.draw();
        stage.act();
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
