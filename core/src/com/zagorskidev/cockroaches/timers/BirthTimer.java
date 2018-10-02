package com.zagorskidev.cockroaches.timers;

import com.zagorskidev.cockroaches.population.Population;
import com.zagorskidev.cockroaches.system.Parameters;

public class BirthTimer extends Timer{

	private Population cockroaches;
	
	public BirthTimer(Population cockroaches) {
		super(Parameters.BIRTH_INTERVAL);
		this.cockroaches = cockroaches;
	}

	@Override
	protected void performAction() {
		cockroaches.birth();
	}
}
