package com.ungabunga.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ungabunga.EngimonGame;
//import com.ungabunga.Pokemon;


public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Engimon game";
		config.height = 720;
		config.width = 1280;
		config.vSyncEnabled = true;

		new LwjglApplication(new EngimonGame(), config);
	}
}
