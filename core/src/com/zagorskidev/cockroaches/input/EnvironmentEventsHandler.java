package com.zagorskidev.cockroaches.input;

import com.badlogic.gdx.InputAdapter;
import com.zagorskidev.cockroaches.Cockroaches;
import com.zagorskidev.cockroaches.system.Parameters;
import com.zagorskidev.cockroaches.timers.Timer;

public class EnvironmentEventsHandler extends InputAdapter {

	private WallsFactory wallsFactory;
	private Cockroaches game;
	
	public EnvironmentEventsHandler(Cockroaches game) {
		wallsFactory = WallsFactory.getInstance();
		this.game = game;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		if(button > 0 || pointer > 0)
			game.start();
		else
			wallsFactory.addPoint(scaleX(screenX), scaleY(screenY));
		
		return true;
	}

	private float scaleX(float screenX) {
		return screenX * Parameters.X_FIELDS / Parameters.GAME_WIDTH;
	}

	private float scaleY(float screenY) {
		return (Parameters.GAME_HEIGHT - screenY) * Parameters.Y_FIELDS / Parameters.GAME_HEIGHT;
	}
}
