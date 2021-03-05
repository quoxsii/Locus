package com.quoxsii.locus.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.quoxsii.locus.LocusGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new LocusGame(), config);
		config.foregroundFPS = 60;
		config.width = LocusGame.SCREEN_WIDTH;
		config.height = LocusGame.SCREEN_HEIGHT;
		config.resizable = false;
	}
}
