package com.zagorskidev.cockroaches.population;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;

import com.zagorskidev.cockroaches.system.Parameters;

public class Genome {

	private Set<Entry<Sequence, Long>> genome;
	private long probabilitySum;

	private ReentrantLock lock;
	
	public Genome() {
		lock = new ReentrantLock();
		
		genome = new TreeSet<>((b, a) -> {
			return Integer.compare(a.getKey().getLength(), b.getKey().getLength());
		});
		probabilitySum = 0;
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
		Entry<Sequence, Long> firstParent = getNextParent();
		Entry<Sequence, Long> secondParent;
		do
			secondParent = getNextParent();
		while(firstParent == secondParent);
		
		return new CrossingOver().cross(firstParent.getKey(), secondParent.getKey());
	}

	private Entry<Sequence, Long> getNextParent() {
		long index = ThreadLocalRandom.current().nextLong(probabilitySum);
		for(Entry<Sequence, Long> sequence : genome) {
			if(index < sequence.getValue())
				return sequence;
		}
		return null;
	}

	public void addSequence(Sequence propagatedSequence) {
		lock.lock();
		try {
			propagatedSequence.mutate();
			genome.add(new SimpleEntry<>(propagatedSequence, 0l));
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
			Entry<Sequence, Long> shortestSequence = genome.iterator().next();
			genome.remove(shortestSequence);
			
			System.out.println("Removed sequence with length " + shortestSequence.getKey().getLength());
		}
	}

	private void enumerate() {
		probabilitySum = 0;
		for(Entry<Sequence, Long> sequence : genome) {
			probabilitySum += sequence.getKey().getLength();
			sequence.setValue(probabilitySum);
		}
	}
}
