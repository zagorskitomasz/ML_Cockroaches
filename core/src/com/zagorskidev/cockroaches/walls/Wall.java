package com.zagorskidev.cockroaches.walls;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;

public class Wall {
	
	private final Vector2 begin;
	private final Vector2 end;
	
	public Wall(Vector2 begin, Vector2 end) {
		this.begin = begin;
		this.end = end;
	}

	public float distanceTo(Vector2 point) {
		return Intersector.distanceSegmentPoint(begin, end, point);
	}

	public Vector2 getBegin() {
		return begin;
	}
	
	public Vector2 getEnd() {
		return end;
	}
}
