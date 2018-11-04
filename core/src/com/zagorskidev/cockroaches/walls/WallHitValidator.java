package com.zagorskidev.cockroaches.walls;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.zagorskidev.cockroaches.system.Parameters;

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
		
		Vector2 leftDown = new Vector2(0, 0);
		Vector2 leftUp = new Vector2(0, (int)Parameters.Y_FIELDS);
		Vector2 rightDown = new Vector2((int)Parameters.X_FIELDS, 0);
		Vector2 rightUp = new Vector2((int)Parameters.X_FIELDS, (int)Parameters.Y_FIELDS);
		
		walls.add(new Wall(leftDown, leftUp));
		walls.add(new Wall(leftUp, rightUp));
		walls.add(new Wall(rightUp, rightDown));
		walls.add(new Wall(rightDown, leftDown));
	}

	public boolean validate(int x, int y) {

		for(Wall wall : walls) {
			if(wall.distanceTo(new Vector2(x, y)) <= Parameters.WALL_WIDTH)
				return true;
		}
		return false;
	}
	
	public List<Wall> getWalls(){
		return walls;
	}

	public void addWall(Wall wall) {
		walls.add(wall);
	}
}
