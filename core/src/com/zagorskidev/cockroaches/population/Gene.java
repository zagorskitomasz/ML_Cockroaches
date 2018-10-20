package com.zagorskidev.cockroaches.population;

import java.util.concurrent.ThreadLocalRandom;

import com.zagorskidev.cockroaches.system.Direction;
import com.zagorskidev.cockroaches.system.Parameters;

public enum Gene {
	LEFT,
	RIGHT,
	FORWARD;
	
	public Direction transform(Direction direction){
		switch(this) {
		case LEFT:
			return transformLeft(direction);
		case RIGHT:
			return transformRight(direction);
		default:
			return direction;
		}
	}

	private Direction transformLeft(Direction direction) {

		if(direction.X == 0)
			return new Direction(direction.X - direction.Y, 0);

		return new Direction(0, direction.Y + direction.X);
	}

	private Direction transformRight(Direction direction) {

		if(direction.X == 0)
			return new Direction(direction.X + direction.Y, 0);

		return new Direction(0, direction.Y - direction.X);
	}

	public static Gene getRandom() {
		
		int random = ThreadLocalRandom.current().nextInt(Parameters.MOVES_ON_TURN);
		
		if(random > 1)
			random = 2;
		
		return values()[random];
	}
}
