package com.zagorskidev.cockroaches.population;

import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

import com.zagorskidev.cockroaches.system.Movement;
import com.zagorskidev.cockroaches.system.Parameters;

public class CrossingOver {

	private Iterator<Movement> currentParent;
	private Iterator<Movement> sleepingParent;
	
	private int currentSize;
	private int sleepingSize;
	
	private Sequence child;
	
	public synchronized Sequence cross(Sequence firstParent, Sequence secondParent) {
		currentParent = firstParent.getMovements().iterator();
		sleepingParent = secondParent.getMovements().iterator();
		
		child = new GeneticSequence();
		cross();
		
		return child;
	}

	private void cross() {
		while(currentParent.hasNext() || sleepingParent.hasNext()) {
			if(checkSwitch())
				doSwitch();
			child.addMovement(currentParent.next());
		}
	}

	private boolean checkSwitch() {
		if(!sleepingParent.hasNext())
			return false;
		
		return !currentParent.hasNext() || ThreadLocalRandom.current().nextInt(100) < Parameters.SWITCH_PROBABILITY;
	}

	private void doSwitch() {
		Iterator<Movement> tempParent = currentParent;
		currentParent = sleepingParent;
		sleepingParent = tempParent;
	}
	
	public synchronized Sequence join(Sequence firstParent, Sequence secondParent) {
		currentParent = firstParent.getMovements().iterator();
		sleepingParent = secondParent.getMovements().iterator();
		
		currentSize = firstParent.getLength();
		sleepingSize = secondParent.getLength();
		
		child = new GeneticSequence();
		join();
		
		return child;
	}

	private void join() {
		if(ThreadLocalRandom.current().nextBoolean())
			addParent(currentParent, currentSize);
		else
			addParent(sleepingParent, sleepingSize);
	}

	private void addParent(Iterator<Movement> parent, int size) {
		int counter = 0;
		while(counter * 2 <= size && parent.hasNext()) {
			child.addMovement(parent.next());
			counter++;
		}
	}

	public Chromosome cross(Genotype genotype) {
		// TODO Auto-generated method stub
		return null;
	}
}
