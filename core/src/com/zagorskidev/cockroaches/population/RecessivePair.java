package com.zagorskidev.cockroaches.population;

import java.util.HashMap;
import java.util.Map;

public class RecessivePair {

	private int distance;
	private Gene gene;
	private Map<Chromosomes, Integer> index;
	
	public RecessivePair() {
		
		distance = Integer.MAX_VALUE;
		gene = Gene.FORWARD;
		
		index = new HashMap<>();
		index.put(Chromosomes.A, 0);
		index.put(Chromosomes.B, 0);
	}
	
	public int getDistance() {
		return distance;
	}

	public Gene getGene() {
		return gene;
	}

	public void setIndex(Chromosomes chromosomeType, Integer indexValue) {
		index.put(chromosomeType, indexValue);
		distance = Math.abs(index.get(Chromosomes.A) - index.get(Chromosomes.B));
	}

	public void setGene(Gene gene) {
		this.gene = gene;
	}

	public Integer getIndex(Chromosomes chromosomeType) {
		return index.get(chromosomeType);
	}

	public int getNewIndex() {
		return (index.get(Chromosomes.A) + index.get(Chromosomes.B)) / 2;
	}
}
