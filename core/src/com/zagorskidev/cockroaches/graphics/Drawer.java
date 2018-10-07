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

public class Drawer {

	private Collection<Cockroach> cockroaches;
	private ShapeRenderer renderer;
	
	private boolean drawMouseShadow;
	
	public Drawer(Population cockroaches, boolean isDesktop) {
		this.cockroaches = cockroaches.getCockroaches();
		renderer = new ShapeRenderer();
		drawMouseShadow = isDesktop;
	}

	public void draw() {
		clear();
		drawCockroaches();
		
		if(drawMouseShadow)
			drawMouseShadow();
		
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

	private float scale(float coord) {
		return coord * Parameters.COCKROACH_RADIUS;
	}
	
	private void drawMouseShadow() {
		renderer.setColor(Color.RED);
		renderer.begin(ShapeType.Line);
		
		int radius = (int)(Parameters.HIT_THRESHOLD * Parameters.COCKROACH_RADIUS);
		
		int x = Gdx.input.getX() - radius;
		int y = Parameters.GAME_HEIGHT - Gdx.input.getY() - radius;
		renderer.ellipse(x, y, radius * 2, radius * 2);
		
		renderer.end();
	}

	private void drawEscapeThreshold() {
		renderer.setColor(Color.GREEN);
		renderer.begin(ShapeType.Line);
		
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
