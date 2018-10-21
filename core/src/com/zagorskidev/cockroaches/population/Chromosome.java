package com.zagorskidev.cockroaches.population;

import java.util.LinkedList;
import java.util.List;

public class Chromosome {

	private List<Gene> genes;
	private double attractivity;
	
	public Chromosome(){
		genes = new LinkedList<>();
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

	public void setAttractivity(double attractivity) {
		this.attractivity = attractivity;
	}
	
	public double getAttractivity() {
		return attractivity;
	}

	public Chromosome trimTo(int length) {
		
		return new Chromosome(genes.subList(0, length));
	}
}
