package com.zagorskidev.cockroaches.system;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

public class WallHitValidator {

	private static WallHitValidator instance;
	
	public static WallHitValidator getInstance() {
		if(instance == null)
			instance = new WallHitValidator();
		
		return instance;
	}
	
	private List<Wall> walls;
	
	private WallHitValidator() {
		walls = new LinkedList<>();
		initWalls();
	}
	
	private void initWalls() {
		
		Point leftDown = new Point(0, 0);
		Point leftUp = new Point(0, (int)Parameters.Y_FIELDS);
		Point rightDown = new Point((int)Parameters.X_FIELDS, 0);
		Point rightUp = new Point((int)Parameters.X_FIELDS, (int)Parameters.Y_FIELDS);
		
		walls.add(new Wall(leftDown, leftUp));
		walls.add(new Wall(leftUp, rightUp));
		walls.add(new Wall(rightUp, rightDown));
		walls.add(new Wall(rightDown, leftDown));
	}

	public boolean validate(int x, int y) {

		for(Wall wall : walls) {
			if(wall.distanceTo(x, y) <= Parameters.WALL_WIDTH)
				return true;
		}
		return false;
	}
	
	public List<Wall> getWalls(){
		return walls;
	}

	public void addWall(Point begin, Point end) {
		walls.add(new Wall(begin, end));
	}
}
