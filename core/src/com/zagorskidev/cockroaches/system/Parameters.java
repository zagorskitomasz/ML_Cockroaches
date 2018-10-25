package com.zagorskidev.cockroaches.system;

import com.badlogic.gdx.Gdx;

public class Parameters {

	public static final int GAME_WIDTH = Gdx.graphics.getWidth();
	public static final int GAME_HEIGHT = Gdx.graphics.getHeight();
	
	public static final long BIRTH_INTERVAL = 0;
	public static final long MOVE_INTERVAL = 0;

	public static final int X_FIELDS = 300;
	public static final float COCKROACH_RADIUS = GAME_WIDTH / X_FIELDS;
	public static final int Y_FIELDS = GAME_HEIGHT == GAME_WIDTH ? X_FIELDS : (int)(GAME_HEIGHT / COCKROACH_RADIUS);
	
	public static final int X_SPAWN = (int)(X_FIELDS * 0.25);
	public static final int Y_SPAWN = (int)(Y_FIELDS * 0.25);
	
	public static final int X_ESCAPE = (int)(X_FIELDS * 0.85);
	public static final int Y_ESCAPE = (int)(Y_FIELDS * 0.8);
	public static final int ESCAPE_THRESHOLD = (int)(X_FIELDS * 0.1);
	
	public static final int HIT_THRESHOLD = 0;
	public static final int GENOME_SIZE = 20;
	public static final int MUTATION_PROBABILITY = 0;
	public static final int SWITCH_PROBABILITY = 1;
	public static final int MOVES_ON_TURN = 20;
	public static final int MAX_RECESSIVE_PAIR_DISTANCE = 10;
	
	public static final Direction DEFAULT_DIRECTION = new Direction(0,1);
}
