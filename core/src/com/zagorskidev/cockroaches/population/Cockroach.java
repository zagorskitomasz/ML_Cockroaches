package com.zagorskidev.cockroaches.population;

import com.zagorskidev.cockroaches.system.CoordsGenerator;
import com.zagorskidev.cockroaches.system.Movement;
import com.zagorskidev.cockroaches.system.Parameters;

public class Cockroach {

	private int x;
	private int y;
	
	private Sequence inheritedSequence;
	private Sequence propagatedSequence;

	public Cockroach(Sequence sequence) {
		inheritedSequence = sequence;
		propagatedSequence = new GeneticSequence();
		generateCoords();
	}

	private void generateCoords() {
		CoordsGenerator generator = CoordsGenerator.getInstance();
		x = generator.getCockroachX();
		y = generator.getCockroachY();
	}

	public boolean move() {
		Movement movement = inheritedSequence.getNextMovement();
		move(movement);
		propagatedSequence.addMovement(movement);
		
		return escaped();
	}

	private void move(Movement movement) {
		x += movement.getDeltaX();
		y += movement.getDeltaY();
	}

	private boolean escaped() {
		return Math.sqrt(
					Math.pow(x - Parameters.X_ESCAPE, 2) + 
					Math.pow(y - Parameters.Y_ESCAPE, 2)) 
				<= Parameters.ESCAPE_THRESHOLD;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Sequence propagateSequence() {
		return propagatedSequence;
	}
}
