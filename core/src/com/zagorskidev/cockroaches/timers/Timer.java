package com.zagorskidev.cockroaches.timers;

import com.badlogic.gdx.utils.TimeUtils;

public abstract class Timer {

	private final long interval;
	private long lastCheck;
	
	public Timer(long interval)	{
		
		this.interval = interval;
		lastCheck = TimeUtils.millis();
	}
	
	public void checkAndPerform() {
		
		long currentTime = TimeUtils.millis();
		
		if(currentTime > lastCheck + interval) {
			performAction();
			lastCheck = currentTime;
		}
	}
	
	protected abstract void performAction();
}
