package com.zagorskidev.cockroaches.walls;

import java.util.List;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.zagorskidev.cockroaches.system.Parameters;

public class WallsValidator {
	
	private List<Wall> walls;
	
	public WallsValidator() {
		walls = WallHitValidator.getInstance().getWalls();
	}

	public boolean validate(Wall pretendingWall) {
		for(Wall existingWall : walls) {
			if(!validate(existingWall, pretendingWall)) {
				System.out.println("Wall validation negative");
				return false;
			}
		}
		return true;
	}

	private boolean validate(Wall first, Wall second) {
		
		if(checkCrossing(first, second))
			return false;
		
		if(checkDistance(first.getBegin(), second))
			return false;
		
		if(checkDistance(first.getEnd(), second))
			return false;
		
		if(checkDistance(second.getBegin(), first))
			return false;
		
		if(checkDistance(second.getEnd(), first))
			return false;
		
		return true;
	}

	private boolean checkCrossing(Wall first, Wall second) {
		return Intersector.intersectSegments(
				first.getBegin().x, 
				first.getBegin().y, 
				first.getEnd().x, 
				first.getEnd().y, 
				second.getBegin().x, 
				second.getBegin().y, 
				second.getEnd().x, 
				second.getEnd().y, 
				null);
	}

	private boolean checkDistance(Vector2 point, Wall wall) {
		return wall.distanceTo(point) <= 2 * Parameters.WALL_WIDTH;
	}
}
