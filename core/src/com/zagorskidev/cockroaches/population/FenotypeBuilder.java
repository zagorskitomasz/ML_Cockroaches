package com.zagorskidev.cockroaches.population;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.zagorskidev.cockroaches.system.Parameters;

public class FenotypeBuilder {

	private List<Gene> parentA;
	private List<Gene> parentB;
	
	private List<Gene> child;
	
	private RecessivePair currentPair;
	private FindingEngine findingEngine;
	
	public FenotypeBuilder(Genotype genotype) {
		
		parentA = new ArrayList<>(genotype.getChromosome(Chromosomes.A).getGenes());
		parentB = new ArrayList<>(genotype.getChromosome(Chromosomes.B).getGenes());
	}

	public List<Gene> build() {
		
		findingEngine = new FindingEngine(parentA, parentB);
		
		initChild();
		
		do {
			findNearestRecessivePair();
			updateChildWithRecessiveGene();
		}
		while(currentPair.getDistance() <= Parameters.MAX_RECESSIVE_PAIR_DISTANCE);
		
		return new LinkedList<>(child);
	}

	private void findNearestRecessivePair() {
		
		currentPair = findingEngine.find();
	}

	private void updateChildWithRecessiveGene() {
		if(currentPair.getDistance() > Parameters.MAX_RECESSIVE_PAIR_DISTANCE)
			return;
		
		int index = currentPair.getNewIndex();
		Gene gene = currentPair.getGene();
		
		child.set(index, gene);
	}

	private void initChild() {
		
		int size = parentA.size();
		child = new ArrayList<>(size);
		
		for(int i = 0; i < size; i++)
			child.add(Gene.FORWARD);
	}
}
