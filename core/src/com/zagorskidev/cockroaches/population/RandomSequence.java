package com.zagorskidev.cockroaches.population;

import java.util.List;

import com.zagorskidev.cockroaches.system.Movement;

public class RandomSequence implements Sequence{
	
	@Override
	public Movement getNextMovement() {
		return Movement.getRandom();
	}

	@Override
	public void addMovement(Movement movement) {
		throw new UnsupportedOperationException("Basic sequence can't be programmed");
	}
	
	@Override
	public int getLength() {
		throw new UnsupportedOperationException("Random sequence has infinite length");
	}

	@Override
	public void mutate() {
		throw new UnsupportedOperationException("Random sequence can't be mutated");
	}

	@Override
	public List<Movement> getMovements() {
		throw new UnsupportedOperationException("Random sequence hasn't movements list");
	}
}
