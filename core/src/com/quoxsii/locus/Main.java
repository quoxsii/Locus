package com.quoxsii.locus;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.quoxsii.locus.screens.GameScreen;
import com.quoxsii.locus.screens.MenuScreen;

public class Main extends Game {
	public static final int GAME_WIDTH = 480;
	public static final int GAME_HEIGHT = 720;

	public SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
