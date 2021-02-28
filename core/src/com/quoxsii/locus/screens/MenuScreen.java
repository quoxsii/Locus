package com.quoxsii.locus.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.quoxsii.locus.Main;

public class MenuScreen implements Screen {
    private static final int BUTTON_WIDTH = 150;
    private static final int BUTTON_HEIGHT = 50;

    Main game;

    Texture playButton;
    Texture exitButton;

    public MenuScreen(Main game) {
        this.game = game;
        playButton = new Texture("button_play.png");
        exitButton = new Texture("button_exit.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.04f, 0.06f, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        int buttonXCenter = Main.GAME_WIDTH / 2 - BUTTON_WIDTH / 2;
        int playButtonYCenter = Main.GAME_HEIGHT / 2 - BUTTON_HEIGHT / 2 + 30;
        int exitButtonYCenter = Main.GAME_HEIGHT / 2 - BUTTON_HEIGHT / 2 - 30;

        if (Gdx.input.getX() < buttonXCenter + BUTTON_WIDTH
                && Gdx.input.getX() > buttonXCenter
                && Main.GAME_HEIGHT - Gdx.input.getY() < playButtonYCenter + BUTTON_HEIGHT
                && Main.GAME_HEIGHT - Gdx.input.getY() > playButtonYCenter) {
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new GameScreen(game));
            }
        }

        if (Gdx.input.getX() < buttonXCenter + BUTTON_WIDTH
                && Gdx.input.getX() > buttonXCenter
                && Main.GAME_HEIGHT - Gdx.input.getY() < exitButtonYCenter + BUTTON_HEIGHT
                && Main.GAME_HEIGHT - Gdx.input.getY() > exitButtonYCenter) {
            if (Gdx.input.isTouched()) {
                Gdx.app.exit();
            }
        }

        game.batch.begin();
        game.batch.draw(playButton, buttonXCenter, playButtonYCenter, BUTTON_WIDTH, BUTTON_HEIGHT);
        game.batch.draw(exitButton, buttonXCenter, exitButtonYCenter, BUTTON_WIDTH, BUTTON_HEIGHT);
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
        playButton.dispose();
        exitButton.dispose();
    }
}
