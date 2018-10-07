package com.zagorskidev.cockroaches.population.generators;

import java.util.LinkedList;
import java.util.List;

import com.zagorskidev.cockroaches.system.Movement;

public class Up implements SequenceGenerator {

	@Override
	public List<Movement> generate() {
		List<Movement> sequence = new LinkedList<>();
		
		sequence.add(Movement.UP);
		sequence.add(Movement.UP);
		sequence.add(Movement.UP);
		sequence.add(Movement.UP);
		sequence.add(Movement.UP);
		sequence.add(Movement.UP);
		sequence.add(Movement.UP);
		sequence.add(Movement.UP);
		sequence.add(Movement.UP);
		sequence.add(Movement.UP);
		
		return sequence;
	}
}
