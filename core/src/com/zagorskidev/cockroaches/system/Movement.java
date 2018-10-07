package com.zagorskidev.cockroaches.system;

import java.util.concurrent.ThreadLocalRandom;

public enum Movement {
	
	UP(0,-1),
	RIGHT(1,0),
	DOWN(0,1),
	LEFT(-1,0);

	private int deltaX;
	private int deltaY;
	
	private Movement(int deltaX, int deltaY) {
		this.deltaX = deltaX;
		this.deltaY = deltaY;
	}

	public int getDeltaX() {
		return deltaX;
	}

	public int getDeltaY() {
		return deltaY;
	}
	
	public static Movement getRandom() {
		int movementIndex = ThreadLocalRandom.current().nextInt(Movement.values().length);
		return Movement.values()[movementIndex];
	}
}
