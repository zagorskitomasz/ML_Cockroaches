package com.zagorskidev.cockroaches.population;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Population {

	private Collection<Cockroach> cockroaches;
	private Genome genome;
	private long counter;
	
	public Population(Genome genome) {
		cockroaches = new ConcurrentLinkedQueue<>();
		this.genome = genome;
		counter = 0;
	}
	
	public void birth() {
		Genotype genotype = genome.getNextGenotype();
		
		Fenotype fenotype;
		
		if(genotype == null)
			fenotype = new Fenotype();
		else
			fenotype = new Fenotype(genotype);
			
		cockroaches.add(new Cockroach(fenotype));
	}

	public Collection<Cockroach> getCockroaches() {
		return cockroaches;
	}

	public void processEscape(Cockroach cockroach) {
		
		Chromosome chromosome = cockroach.propagateChromosome();
		double chromosomeAttractivity = calculateAttractivity(cockroach.getSuccessDist(), chromosome.getLength()); 
				
		genome.addChromosome(chromosome, chromosomeAttractivity);
		System.out.println("Success count: " + ++counter);
		
		cockroaches.remove(cockroach);
	}

	private double calculateAttractivity(double successDist, int length) {
		
		return (100 / successDist) * (1000 / length); 
	}

}
