package com.quoxsii.locus.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.quoxsii.locus.Main;

import java.awt.Menu;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new Main(), config);
		config.foregroundFPS = 60;
		config.width = Main.GAME_WIDTH;
		config.height = Main.GAME_HEIGHT;
		config.resizable = false;
	}
}
