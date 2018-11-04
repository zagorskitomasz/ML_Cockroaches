package com.zagorskidev.cockroaches.walls;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.zagorskidev.cockroaches.system.Parameters;

public class SuccessDistCalculator {
	
	private List<Wall> walls;
	
	private float successX;
	private float successY;
	
	private float currentX;
	private float currentY;
	
	private float distance;
	
	public SuccessDistCalculator() {
		walls = WallHitValidator.getInstance().getWalls();
		successX = Parameters.X_ESCAPE;
		successY = Parameters.Y_ESCAPE;
	}
	
	public SuccessDistCalculator(float destX, float destY) {
		walls = WallHitValidator.getInstance().getWalls();
		successX = destX;
		successY = destY;
	}

	public double calculate(float x, float y) {
		currentX = x;
		currentY = y;
		distance = 0;
		
		while(currentX != successX || currentY != successY) {
			Map<Wall,Vector2> collidedWalls = checkCollisions();
			
			if(collidedWalls.size() == 0)
				return distance + calculateStraightEscapeDist(currentX, currentY);
			
			Wall nearestWall = chooseNearestWall(collidedWalls);
			Vector2 corner = chooseCorner(nearestWall);
			goToCornerRecursively(corner);
		}
		return distance;
	}

	public double calculateStraightEscapeDist(float currentX, float currentY) {

		return Vector2.dst(currentX, currentY, successX, successY);
	}

	private Map<Wall, Vector2> checkCollisions() {
		
		Map<Wall, Vector2> collidedWalls = new HashMap<>();
		
		for(Wall wall : walls) {
			Vector2 crossingPoint = new Vector2();
			if(isCrossed(wall, crossingPoint) && !isWallsEnd(wall))
				collidedWalls.put(wall, crossingPoint);
		}
		return collidedWalls;
	}

	private boolean isCrossed(Wall wall, Vector2 crossingPoint) {
		return Intersector.intersectSegments(
				currentX, 
				currentY, 
				successX, 
				successY, 
				wall.getBegin().x, 
				wall.getBegin().y, 
				wall.getEnd().x, 
				wall.getEnd().y, 
				crossingPoint);
	}

	private boolean isWallsEnd(Wall wall) {
		
		return (currentX == wall.getBegin().x && currentY == wall.getBegin().y)
				|| (currentX == wall.getEnd().x	&& currentY == wall.getEnd().y)
				|| (successX == wall.getBegin().x && successY == wall.getBegin().y)
				|| (successX == wall.getEnd().x	&& successY == wall.getEnd().y);
	}

	private Wall chooseNearestWall(Map<Wall, Vector2> collidedWalls) {
		Wall nearestWall = null;
		double nearestDist = Double.MAX_VALUE;
		
		for(Entry<Wall, Vector2> currentWall : collidedWalls.entrySet()) {
			double currentDist = currentWall.getValue().dst(currentX, currentY);
			if(currentDist < nearestDist) {
				nearestDist = currentDist;
				nearestWall = currentWall.getKey();
			}
		}
		return nearestWall;
	}

	private Vector2 chooseCorner(Wall nearestWall) {
		float distBegin = nearestWall.getBegin().dst(successX, successY);
		float distEnd = nearestWall.getEnd().dst(successX, successY);
		
		return distBegin < distEnd ? nearestWall.getBegin() : nearestWall.getEnd();
	}

	private void goToCornerRecursively(Vector2 corner) {
		SuccessDistCalculator calculator = new SuccessDistCalculator(corner.x, corner.y);
		distance += calculator.calculate(currentX, currentY);
		currentX = corner.x;
		currentY = corner.y;
	}
}
