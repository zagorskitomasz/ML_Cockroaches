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
	
	public Drawer(Population cockroaches) {
		this.cockroaches = cockroaches.getCockroaches();
		renderer = new ShapeRenderer();
	}

	public void draw() {
		clear();
		drawCockroaches();
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

	private void clear() {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
	}

	public void dispose() {
		renderer.dispose();
	}

}
