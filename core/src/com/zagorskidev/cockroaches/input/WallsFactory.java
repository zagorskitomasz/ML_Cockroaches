package com.zagorskidev.cockroaches.input;

import java.awt.Point;

import com.zagorskidev.cockroaches.system.WallHitValidator;

public class WallsFactory {
	
	private static WallsFactory instance;

	public static WallsFactory getInstance() {
		if(instance == null)
			instance = new WallsFactory();
		
		return instance;
	}
	
	private WallHitValidator validator;
	private Point begin;

	private WallsFactory() {
		validator = WallHitValidator.getInstance();
	}

	public void addPoint(float x, float y) {
		
		synchronized(WallsFactory.class) {
			addNextPoint(x, y);
		}
	}

	private void addNextPoint(float x, float y) {
		
		Point point = new Point((int)x, (int)y);
		
		if(begin == null)
			begin = point;
		else {
			validator.addWall(begin, point);
			begin = null;
		}
	}
	
	
}
