package com.zagorskidev.cockroaches.system;

import java.awt.Point;

import com.badlogic.gdx.math.Intersector;

public class Wall {
	
	private final Point begin;
	private final Point end;
	
	public Wall(Point begin, Point end) {
		this.begin = begin;
		this.end = end;
	}

	public float distanceTo(int x, int y) {
		return Intersector.distanceSegmentPoint(begin.x, begin.y, end.x, end.y, x, y);
	}

	public Point getBegin() {
		return begin;
	}
	
	public Point getEnd() {
		return end;
	}
}
