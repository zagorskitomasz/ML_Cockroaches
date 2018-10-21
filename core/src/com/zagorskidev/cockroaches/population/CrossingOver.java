package com.zagorskidev.cockroaches.population;

import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

import com.zagorskidev.cockroaches.system.Parameters;

public class CrossingOver {

	private Iterator<Gene> currentParent;
	private Iterator<Gene> sleepingParent;
	
	private int length;
	
	private Chromosome child;

	private boolean checkSwitch() {
		if(!sleepingParent.hasNext())
			return false;
		
		return !currentParent.hasNext() || ThreadLocalRandom.current().nextInt(100) < Parameters.SWITCH_PROBABILITY;
	}

	private void doSwitch() {
		Iterator<Gene> tempParent = currentParent;
		currentParent = sleepingParent;
		sleepingParent = tempParent;
	}

	public synchronized Chromosome cross(Genotype genotype) {
		init(genotype);
		
		while(child.getLength() < length) {
			if(checkSwitch())
				doSwitch();
			
			sleepingParent.next();
			Gene gene = currentParent.next();
			
			child.addGene(gene);
		}
		
		return child;
	}

	private void init(Genotype genotype) {
		
		currentParent = genotype.getChromosome(Chromosomes.A).getGenes().iterator();
		sleepingParent = genotype.getChromosome(Chromosomes.B).getGenes().iterator();
		length = genotype.getChromosome(Chromosomes.A).getLength();
		
		child = new Chromosome();
	}
}
