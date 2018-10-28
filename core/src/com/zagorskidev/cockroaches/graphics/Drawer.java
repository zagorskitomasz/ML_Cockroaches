package com.zagorskidev.cockroaches.graphics;

import java.util.Collection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.zagorskidev.cockroaches.population.Cockroach;
import com.zagorskidev.cockroaches.population.Population;
import com.zagorskidev.cockroaches.system.Parameters;
import com.zagorskidev.cockroaches.system.Wall;
import com.zagorskidev.cockroaches.system.WallHitValidator;

public class Drawer {

	private Collection<Cockroach> cockroaches;
	private Collection<Wall> walls;
	
	private ShapeRenderer renderer;
	
	public Drawer(Population cockroaches, boolean isDesktop) {
		this.cockroaches = cockroaches.getCockroaches();
		walls = WallHitValidator.getInstance().getWalls();
		renderer = new ShapeRenderer();
	}

	public void draw() {
		clear();
		drawWalls();
		drawCockroaches();
		
		if(Parameters.ESCAPE_THRESHOLD > 0)
			drawEscapeThreshold();
	}

	private void drawCockroaches() {
		
		renderer.setColor(Color.WHITE);
		renderer.begin(ShapeType.Filled);
		
		for(Cockroach cockroach : cockroaches)
			drawCockroach(cockroach);
		
		renderer.end();
	}

	private void drawCockroach(Cockroach cockroach) {
		float x = scale(cockroach.getX());
		float y = scale(cockroach.getY());
		
		renderer.ellipse(x, y, Parameters.COCKROACH_RADIUS, Parameters.COCKROACH_RADIUS);
	}

	private void drawWalls() {
		
		renderer.setColor(Color.RED);
		renderer.begin(ShapeType.Filled);
		
		for(Wall wall : walls)
			drawWall(wall);
		
		renderer.end();
	}

	private void drawWall(Wall wall) {
		
		float xBegin = scale(wall.getBegin().x);
		float yBegin = scale(wall.getBegin().y);
		
		float xEnd = scale(wall.getEnd().x);
		float yEnd = scale(wall.getEnd().y);
		
		renderer.rectLine(xBegin, yBegin, xEnd, yEnd, scale(Parameters.WALL_WIDTH * 2));
	}

	private float scale(float coord) {
		return coord * Parameters.COCKROACH_RADIUS;
	}

	private void drawEscapeThreshold() {
		
		renderer.setColor(Color.GREEN);
		renderer.begin(ShapeType.Filled);
		
		int radius = (int)(Parameters.ESCAPE_THRESHOLD * Parameters.COCKROACH_RADIUS);
		
		int x = (int)(Parameters.X_ESCAPE * Parameters.COCKROACH_RADIUS) - radius;
		int y = (int)(Parameters.Y_ESCAPE * Parameters.COCKROACH_RADIUS) - radius;
		renderer.ellipse(x, y, radius * 2, radius * 2);
		
		renderer.end();
	}

	private void clear() {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
	}

	public void dispose() {
		renderer.dispose();
	}
}
