package com.zagorskidev.cockroaches;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.zagorskidev.cockroaches.Cockroaches;

public class DesktopLauncher {
	public static void main (String[] arg) {
		
		createApplication();
	}

	private static Application createApplication() {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = "Cockroaches";
		config.useGL30 = true;
		config.height = 700;
		config.width = 400;
		
		return new LwjglApplication(new Cockroaches(), config);
	}
}
