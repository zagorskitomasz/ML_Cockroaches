package com.zagorskidev.cockroaches.population;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.zagorskidev.cockroaches.system.Movement;
import com.zagorskidev.cockroaches.system.Parameters;
import com.zagorskidev.cockroaches.system.exceptions.EmptySequenceException;

public class GeneticSequence implements Sequence{

	protected List<Movement> movements;
	private Iterator<Movement> iterator;
	
	public GeneticSequence(){
		movements = new LinkedList<>();
	}

	public GeneticSequence(List<Movement> movements){
		this();
		this.movements = movements;
	}
	
	@Override
	public Movement getNextMovement() {
		if(movements.size() <= 0)
			throw new EmptySequenceException();
		
		checkIterator();
		return iterator.next();
	}

	private void checkIterator() {
		if(iterator == null || !iterator.hasNext())
			iterator = movements.iterator();
	}

	@Override
	public void addMovement(Movement movement) {
		movements.add(movement);
	}
	
	@Override
	public int getLength() {
		return movements.size();
	}

	@Override
	public void mutate() {
		for(int i = 0; i < movements.size(); i++) {
			if(ThreadLocalRandom.current().nextInt(100) < Parameters.MUTATION_PROBABILITY)
				movements.set(i, Movement.getRandom());
		}
	}

	@Override
	public List<Movement> getMovements() {
		return movements;
	}
}
