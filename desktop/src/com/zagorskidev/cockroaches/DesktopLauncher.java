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
		config.height = 800;
		config.width = 1050;
		
		return new LwjglApplication(new Cockroaches(true), config);
	}
}
