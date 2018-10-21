package com.zagorskidev.cockroaches.population;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;

import com.google.common.collect.Multiset;
import com.google.common.collect.TreeMultiset;
import com.zagorskidev.cockroaches.system.Parameters;

public class Genome {

	private Multiset<Entry<Chromosome, Double>> genome;
	private double probabilitySum;

	private ReentrantLock lock;
	
	public Genome() {
		lock = new ReentrantLock();
		
		genome = TreeMultiset.create((a, b) -> {
			return Double.compare(a.getKey().getAttractivity(), b.getKey().getAttractivity());
		});
		probabilitySum = 0;
	}

	public void addChromosome(Chromosome chromosome) {
		
		lock.lock();
		try {
			genome.add(new SimpleEntry<>(chromosome, 0d));
			trim();
			enumerate();
			
			System.out.println("Added chromosome with attractivity " + chromosome.getAttractivity());
		}
		finally {
			lock.unlock();
		}
	}

	private void trim() {
		if(genome.size() > Parameters.GENOME_SIZE) {
			Entry<Chromosome, Double> weakestChromosome = genome.iterator().next();
			genome.remove(weakestChromosome);
			
			System.out.println("Removed chromosome with attractivity " + weakestChromosome.getKey().getAttractivity());
		}
	}

	private void enumerate() {
		probabilitySum = 0;
		for(Entry<Chromosome, Double> chromosome : genome) {
			probabilitySum += chromosome.getKey().getAttractivity();
			chromosome.setValue(probabilitySum);
		}
	}

	public Genotype getNextGenotype() {
		
		if(genome.size() < Parameters.GENOME_SIZE)
			return null;
		
		lock.lock();
		try {
			return createChild();
		}
		finally {
			lock.unlock();
		}
	}

	private Genotype createChild() {
		Entry<Chromosome, Double> firstParent = getNextParent();
		Entry<Chromosome, Double> secondParent;
		do
			secondParent = getNextParent();
		while(firstParent == secondParent);
		
		return createChild(firstParent.getKey(), secondParent.getKey());
	}

	private Entry<Chromosome, Double> getNextParent() {
		double index = ThreadLocalRandom.current().nextDouble(probabilitySum);
		for(Entry<Chromosome, Double> sequence : genome) {
			if(index < sequence.getValue())
				return sequence;
		}
		return null;
	}

	private Genotype createChild(Chromosome firstParent, Chromosome secondParent) {
		
		if(firstParent.getLength() < secondParent.getLength())
			return new Genotype(firstParent, secondParent.trimTo(firstParent.getLength()));

		if(secondParent.getLength() < firstParent.getLength())
			return new Genotype(secondParent, firstParent.trimTo(secondParent.getLength()));
		
		return new Genotype(firstParent, secondParent);
		
	}
}
