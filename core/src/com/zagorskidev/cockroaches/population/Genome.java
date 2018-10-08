package com.zagorskidev.cockroaches.population;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;

import com.google.common.collect.Multiset;
import com.google.common.collect.TreeMultiset;
import com.zagorskidev.cockroaches.system.Parameters;

public class Genome {

	private Multiset<Entry<Sequence, Double>> genome;
	private double probabilitySum;
	private double lastSeq;

	private ReentrantLock lock;
	
	public Genome() {
		lock = new ReentrantLock();
		
		genome = TreeMultiset.create((b, a) -> {
			return Integer.compare(a.getKey().getLength(), b.getKey().getLength());
		});
		probabilitySum = 0;
		lastSeq = 1;
	}
	
	public Sequence getNextSequence() {		
		if(genome.size() < Parameters.GENOME_SIZE)
			return new RandomSequence();
		
		lock.lock();
		try {
			return createChild();
		}
		finally {
			lock.unlock();
		}
	}

	private Sequence createChild() {
		Entry<Sequence, Double> firstParent = getNextParent();
		Entry<Sequence, Double> secondParent;
		do
			secondParent = getNextParent();
		while(firstParent == secondParent);
		
		return new CrossingOver().cross(firstParent.getKey(), secondParent.getKey());
	}

	private Entry<Sequence, Double> getNextParent() {
		double index = ThreadLocalRandom.current().nextDouble(probabilitySum);
		for(Entry<Sequence, Double> sequence : genome) {
			if(index < sequence.getValue())
				return sequence;
		}
		return null;
	}

	public void addSequence(Sequence propagatedSequence) {
		lock.lock();
		try {
			propagatedSequence.mutate();
			genome.add(new SimpleEntry<>(propagatedSequence, 0d));
			lastSeq = propagatedSequence.getLength();
			trim();
			enumerate();
			
			System.out.println("Added sequence with length " + propagatedSequence.getLength());
		}
		finally {
			lock.unlock();
		}
	}

	private void trim() {
		if(genome.size() > Parameters.GENOME_SIZE) {
			Entry<Sequence, Double> longestSequence = genome.iterator().next();
			genome.remove(longestSequence);
			
			System.out.println("Removed sequence with length " + longestSequence.getKey().getLength());
		}
	}

	private void enumerate() {
		probabilitySum = 0;
		for(Entry<Sequence, Double> sequence : genome) {
			probabilitySum += lastSeq / Math.pow(sequence.getKey().getLength(), 2);
			sequence.setValue(probabilitySum);
		}
	}
}
