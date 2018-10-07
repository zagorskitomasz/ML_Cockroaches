package com.zagorskidev.cockroaches.population;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.zagorskidev.cockroaches.system.Parameters;

public class Population {

	private Collection<Cockroach> cockroaches;
	private Genome genome;
	
	public Population(Genome genome) {
		cockroaches = new ConcurrentLinkedQueue<>();
		this.genome = genome;
	}
	
	public void birth() {
		Sequence sequence = genome.getNextSequence();
		cockroaches.add(new Cockroach(sequence));
	}

	public Collection<Cockroach> getCockroaches() {
		return cockroaches;
	}

	public void processHit(int x, int y) {
		Iterator<Cockroach> iterator = cockroaches.iterator();
		
		while(iterator.hasNext()){
			Cockroach cockroach = iterator.next();
			if(gotHit(cockroach, x, y)){
				extractSequence(cockroach);
				iterator.remove();
			}
		}
	}

	private boolean gotHit(Cockroach cockroach, int x, int y) {
		return Math.abs(x - cockroach.getX()) <= Parameters.HIT_THRESHOLD 
				&& Math.abs(y - cockroach.getY()) <= Parameters.HIT_THRESHOLD;
	}

	private void extractSequence(Cockroach cockroach) {
		genome.addSequence(cockroach.propagateSequence());
	}

	public void processEscape(Cockroach cockroach) {
		genome.addSequence(new SuperSequence(cockroach.propagateSequence()));
		cockroaches.remove(cockroach);
	}

}
