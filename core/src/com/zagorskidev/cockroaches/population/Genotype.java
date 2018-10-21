package com.zagorskidev.cockroaches.population;

import java.util.HashMap;
import java.util.Map;

public class Genotype {

	private Map<Chromosomes, Chromosome> chromosomes;
	
	public Genotype(Chromosome firstParent, Chromosome secondParent) {
		chromosomes = new HashMap<>();
		chromosomes.put(Chromosomes.A, firstParent);
		chromosomes.put(Chromosomes.B, secondParent);
	}

	public Chromosome getChromosome(Chromosomes chromosomeType) {
		return chromosomes.get(chromosomeType);
	}
}
