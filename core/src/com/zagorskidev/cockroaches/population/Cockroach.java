package com.zagorskidev.cockroaches.population;

import com.zagorskidev.cockroaches.system.Direction;
import com.zagorskidev.cockroaches.system.Parameters;
import com.zagorskidev.cockroaches.system.WallHitValidator;

public class Cockroach {

	private int x;
	private int y;
	
	private Direction direction;
	private double successDist;
	private WallHitValidator wallHitValidator;
	
	private Fenotype fenotype;

	public Cockroach(Fenotype fenotype) {
		this.fenotype = fenotype;
		
		successDist = Double.POSITIVE_INFINITY;
		direction = Parameters.DEFAULT_DIRECTION;
		generateCoords();
		
		wallHitValidator = WallHitValidator.getInstance();
	}

	private void generateCoords() {
		x = (int)Parameters.X_SPAWN;
		y = (int)Parameters.Y_SPAWN;
	}

	public GenomeTier move() {
		direction = fenotype.processNextStep(direction);
		doMove();
		
		return escaped();
	}

	private void doMove() {
		x += direction.X;
		y += direction.Y;
	}

	private GenomeTier escaped() {
		
		successDist = Math.sqrt(
				Math.pow(x - Parameters.X_ESCAPE, 2) + 
				Math.pow(y - Parameters.Y_ESCAPE, 2));
		
		if(successDist <= Parameters.ESCAPE_THRESHOLD)
			return GenomeTier.OPTIMIZING;
		
		if(wallHitValidator.validate(x, y)) 
			return GenomeTier.LOOKING_FOR;
			
		return null;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public double getSuccessDist() {
		return successDist;
	}

	public Chromosome propagateChromosome(GenomeTier tier) {
		Chromosome chromosome = fenotype.getChromosome(tier);
		chromosome.setSuccessDist(successDist);
		return chromosome;
	}
}
