package com.zagorskidev.cockroaches.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.zagorskidev.cockroaches.population.Population;

public class Drawer {

	public Drawer(Population cockroaches) {
		// TODO Auto-generated constructor stub
	}

	public void draw() {
		clear();
		// TODO Auto-generated method stub
		
	}
	
	private void clear() {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
	}

	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
