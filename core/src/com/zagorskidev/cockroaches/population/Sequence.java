package com.zagorskidev.cockroaches.population;

import java.util.List;

import com.zagorskidev.cockroaches.system.Movement;

public interface Sequence {

	public Movement getNextMovement();
	public void addMovement(Movement movement);
	public int getLength();
	public void mutate();
	public List<Movement> getMovements();
}
