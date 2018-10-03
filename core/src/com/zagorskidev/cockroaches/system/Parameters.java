package com.zagorskidev.cockroaches.system;

import com.badlogic.gdx.Gdx;

public class Parameters {

	public static final int GAME_WIDTH = Gdx.graphics.getWidth();
	public static final int GAME_HEIGHT = Gdx.graphics.getHeight();
	
	public static final long BIRTH_INTERVAL = 2000;
	public static final long MOVE_INTERVAL = 20;

	public static final int X_FIELDS = 100;
	public static final float COCKROACH_RADIUS = GAME_WIDTH / 100;
	public static final int Y_FIELDS = (int)(GAME_HEIGHT / COCKROACH_RADIUS);	
}
