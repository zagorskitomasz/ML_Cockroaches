package com.zagorskidev.cockroaches.system;

import java.util.Random;

public class CoordsGenerator {
	
	private static CoordsGenerator instance;
	
	public static CoordsGenerator getInstance() {
		if(instance == null)
			instance = new CoordsGenerator();
		
		return instance;
	}

	private Random random;
	
	private CoordsGenerator() {
		random = new Random();
	}

	@SuppressWarnings("unused")
	public int getCockroachX() {
		if(Parameters.X_SPAWN > 0)
			return Parameters.X_SPAWN;
		
		return Parameters.X_MARGIN + random.nextInt(Parameters.X_FIELDS - (2 * Parameters.X_MARGIN));
	}

	public int getCockroachY() {
		if(Parameters.Y_SPAWN > 0)
			return Parameters.Y_SPAWN;
		
		return Parameters.Y_MARGIN + random.nextInt(Parameters.Y_FIELDS - (2 * Parameters.Y_MARGIN));
	}
}
