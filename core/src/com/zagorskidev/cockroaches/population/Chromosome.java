package com.zagorskidev.cockroaches.population;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.zagorskidev.cockroaches.system.Parameters;

public class Chromosome {

	private List<Gene> genes;
	private GenomeTier tier;
	private Double attractivity = null;
	private double successDist;
	
	public Chromosome(){
		genes = new ArrayList<>();
	}
	
	public Chromosome(List<Gene> genes) {
		this.genes = genes;
	}
	
	public int getLength() {
		
		return genes.size();
	}

	public void addGene(Gene gene) {
		genes.add(gene);
	}

	public List<Gene> getGenes() {
		return genes;
	}
	
	public double getAttractivity() {
		switch(tier) {
		case LOOKING_FOR:
			if(attractivity == null)
				attractivity = 10000 / (double)successDist;
			break;
		case OPTIMIZING:
			if(attractivity == null)
				attractivity = 10000 / (double)genes.size();
			break;
		}
		
		return attractivity;
	}

	public Chromosome trimTo(int length) {
		
		return new Chromosome(genes.subList(0, length));
	}

	public void setTier(GenomeTier tier) {
		
		if(Parameters.TRIM && GenomeTier.LOOKING_FOR.equals(tier))
			genes = genes.subList(0, Math.max(0, genes.size() - (int)(Parameters.X_FIELDS * 0.1)));
		
		this.tier = tier;
	}
	
	public GenomeTier getTier() {
		return tier;
	}

	public Chromosome mutate() {
		List<Gene> mutated = new ArrayList<>(genes);
		for(int i = 0; i < (Parameters.MUTATION_PROBABILITY * mutated.size()) / 100; i++){
			mutated.set(ThreadLocalRandom.current().nextInt(mutated.size()), Gene.getRandom());
		}
		return new Chromosome(mutated);
	}
	
	public void setSuccessDist(double successDist) {
		this.successDist = successDist;
	}
}
