package com.zagorskidev.cockroaches.population;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.zagorskidev.cockroaches.system.Direction;

public class Fenotype {

	private List<Gene> fenotype;
	private Iterator<Gene> genesIterator;
	
	private Chromosome child;

	public Fenotype() {
		fenotype = new LinkedList<>();
		genesIterator = fenotype.iterator();
		child = new Chromosome();
	}
	
	public Fenotype(Genotype genotype) {
		encryptFenotype(genotype);
		createChild(genotype);
	}

	private void encryptFenotype(Genotype genotype) {
		FenotypeBuilder builder = new FenotypeBuilder(genotype);
		fenotype = builder.build();
		
		genesIterator = fenotype.iterator();
		child = new Chromosome();
	}

	private void createChild(Genotype genotype) {
		CrossingOver crossingOver = new CrossingOver();
		child = crossingOver.cross(genotype);
	}

	public Chromosome getChromosome() {
		return child;
	}

	public Direction processNextStep(Direction direction) {
		return getNextGene().transform(direction);
	}

	private Gene getNextGene() {
		
		if(genesIterator.hasNext())
			return genesIterator.next();
		
		return addRandomGene();
	}

	private Gene addRandomGene() {
		
		Gene gene = Gene.getRandom();
		child.addGene(gene);
		
		return gene;
	}
}
