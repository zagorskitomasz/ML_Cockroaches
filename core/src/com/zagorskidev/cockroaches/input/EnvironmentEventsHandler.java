package com.zagorskidev.cockroaches.input;

import com.badlogic.gdx.InputAdapter;
import com.zagorskidev.cockroaches.population.Population;
import com.zagorskidev.cockroaches.system.Parameters;

public class EnvironmentEventsHandler extends InputAdapter {

	private Population cockroaches;
	
	public EnvironmentEventsHandler(Population cockroaches) {
		this.cockroaches = cockroaches;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		cockroaches.processHit(scaleX(screenX), scaleY(screenY));
		return true;
	}

	private int scaleX(int screenX) {
		return screenX * Parameters.X_FIELDS / Parameters.GAME_WIDTH;
	}

	private int scaleY(int screenY) {
		return (Parameters.GAME_HEIGHT - screenY) * Parameters.Y_FIELDS / Parameters.GAME_HEIGHT;
	}
}
