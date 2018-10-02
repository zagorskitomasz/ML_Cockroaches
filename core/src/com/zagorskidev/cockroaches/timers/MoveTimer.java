package com.zagorskidev.cockroaches.timers;

import com.zagorskidev.cockroaches.population.Cockroach;
import com.zagorskidev.cockroaches.population.Population;
import com.zagorskidev.cockroaches.system.Parameters;

public class MoveTimer extends Timer{

	private Population cockroaches;
	
	public MoveTimer(Population cockroaches) {
		super(Parameters.MOVE_INTERVAL);
		this.cockroaches = cockroaches;
	}

	@Override
	protected void performAction() {
		
		for(Cockroach cockroach : cockroaches.getCockroaches())
			cockroach.move();
	}
}
