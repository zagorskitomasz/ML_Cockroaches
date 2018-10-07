package com.zagorskidev.cockroaches.population.generators;

import java.util.LinkedList;
import java.util.List;

import com.zagorskidev.cockroaches.system.Movement;

public class Zigzag implements SequenceGenerator {

	@Override
	public List<Movement> generate() {
		List<Movement> sequence = new LinkedList<>();
		
		sequence.add(Movement.UP);
		sequence.add(Movement.LEFT);
		sequence.add(Movement.UP);
		sequence.add(Movement.LEFT);
		sequence.add(Movement.UP);
		sequence.add(Movement.LEFT);
		sequence.add(Movement.DOWN);
		sequence.add(Movement.LEFT);
		sequence.add(Movement.DOWN);
		sequence.add(Movement.LEFT);
		sequence.add(Movement.DOWN);
		sequence.add(Movement.LEFT);
		
		return sequence;
	}
}
