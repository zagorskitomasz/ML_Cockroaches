package com.zagorskidev.cockroaches;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;

public class Cockroaches extends ApplicationAdapter {

	@Override
	public void create () {
		initializeCockroaches();
		startEvolution();
	}
	
	private void initializeCockroaches() {
		//TODO
	}

	private void startEvolution() {
		
		Gdx.app.postRunnable(new Runnable() {
			public void run() {
				//TODO
			}
		});
	}

	@Override
	public void render () {
		
		clear();
		//TODO
	}
	
	private void clear() {
		Gdx.gl.glClearColor(0, 150, 0, 0);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
	}
	
	@Override
	public void dispose () {
		//TODO
	}
}
