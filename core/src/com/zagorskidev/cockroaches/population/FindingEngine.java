package com.zagorskidev.cockroaches.population;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindingEngine {
	
	private Map<Chromosomes,List<Gene>> parents;
	
	private Map<Chromosomes,Integer> currentsLeft;
	private Map<Chromosomes,Integer> currentsRight;
	
	private boolean currentsUpdated;
	private RecessivePair recessivePair;
	
	public FindingEngine(List<Gene> parentA, List<Gene> parentB) {
		
		parents = new HashMap<>();
		
		parents.put(Chromosomes.A, parentA);
		parents.put(Chromosomes.B, parentB);
	}

	public RecessivePair find() {

		recessivePair = new RecessivePair();
		
		currentsLeft = new HashMap<>();
		currentsRight = new HashMap<>();
		
		for(int i = 0; i < parents.get(Chromosomes.A).size(); i++) {
			
			currentsUpdated = false;
			
			updateCurrents(i, Chromosomes.A);
			updateCurrents(i, Chromosomes.B);
			
			if(currentsUpdated)
				processCurrents();
		}
		
		removePairFromParents();
		return recessivePair;
	}

	private void processCurrents() {
		
		checkAndUpdate(currentsLeft, Gene.LEFT);
		checkAndUpdate(currentsRight, Gene.RIGHT);
	}
	
	private void checkAndUpdate(Map<Chromosomes,Integer> currents, Gene gene) {
		
		Integer tempIndexA = currents.get(Chromosomes.A);
		Integer tempIndexB = currents.get(Chromosomes.B);
		
		if(tempIndexA == null || tempIndexB == null)
			return;
		
		if(Math.abs(tempIndexA - tempIndexB) < recessivePair.getDistance()) {
			recessivePair.setIndex(Chromosomes.A, tempIndexA);
			recessivePair.setIndex(Chromosomes.B, tempIndexB);
			recessivePair.setGene(gene);
		}
	}

	private void removePairFromParents() {
		if(recessivePair.getGene() != Gene.FORWARD) {
			parents.get(Chromosomes.A).set(recessivePair.getIndex(Chromosomes.A),Gene.FORWARD);
			parents.get(Chromosomes.B).set(recessivePair.getIndex(Chromosomes.B),Gene.FORWARD);
		}
	}

	private void updateCurrents(int index, Chromosomes chromosomeType) {
		
		switch(parents.get(chromosomeType).get(index)) {
		case LEFT:
			currentsLeft.put(chromosomeType, index);
			currentsUpdated = true;
			break;
		case RIGHT:
			currentsRight.put(chromosomeType, index);
			currentsUpdated = true;
			break;
		default:
			break;
		}
	}
}
