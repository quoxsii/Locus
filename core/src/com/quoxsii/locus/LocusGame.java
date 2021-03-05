package com.quoxsii.locus;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.quoxsii.locus.screens.GameScreen;
import com.quoxsii.locus.screens.MenuScreen;

public class LocusGame extends Game {
	public static final int SCREEN_WIDTH = 480;
	public static final int SCREEN_HEIGHT = 720;

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
