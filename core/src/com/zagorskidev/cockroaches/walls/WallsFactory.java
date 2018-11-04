package com.zagorskidev.cockroaches.walls;

import com.badlogic.gdx.math.Vector2;

public class WallsFactory {
	
	private static WallsFactory instance;

	public static WallsFactory getInstance() {
		if(instance == null)
			instance = new WallsFactory();
		
		return instance;
	}
	
	private WallHitValidator validator;
	private WallsValidator wallsValidator;
	private Vector2 begin;

	private WallsFactory() {
		validator = WallHitValidator.getInstance();
		wallsValidator = new WallsValidator();
	}

	public void addPoint(float x, float y) {
		
		synchronized(WallsFactory.class) {
			addNextPoint(x, y);
		}
	}

	private void addNextPoint(float x, float y) {
		
		Vector2 point = new Vector2((int)x, (int)y);
		
		if(begin == null)
			begin = point;
		else {
			Wall wall = new Wall(begin, point);
			if(wallsValidator.validate(wall))
				validator.addWall(wall);
			begin = null;
		}
	}
	
	
}
