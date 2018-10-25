package com.zagorskidev.cockroaches.population;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

import com.zagorskidev.cockroaches.system.Parameters;

public class Genome {
	
	public enum Action{
		ADD_CHROMOSOME,
		GET_GENOTYPE;
	}
	
	private Map<GenomeTier, List<Entry<Chromosome, Double>>> genome;
	private Map<GenomeTier, Double> probabilitySum;
	
	public Genome() {
		genome = new ConcurrentHashMap<>();
		genome.put(GenomeTier.LOOKING_FOR, new ArrayList<>());
		genome.put(GenomeTier.OPTIMIZING, new ArrayList<>());
		
		probabilitySum = new ConcurrentHashMap<>();
		probabilitySum.put(GenomeTier.LOOKING_FOR, 0d);
		probabilitySum.put(GenomeTier.OPTIMIZING, 0d);
	}
	
	public synchronized Object fireAction(Action action, Object param) {
		switch(action) {
		case ADD_CHROMOSOME:
			addChromosome((Chromosome)param);
			break;
		case GET_GENOTYPE:
			return getNextGenotype();
		}
		return null;
	}

	private void addChromosome(Chromosome chromosome) {
		GenomeTier tier = chromosome.getTier();
			
		genome.get(tier).add(new SimpleEntry<>(chromosome, 0d));
		System.out.println("Added " + tier.name() + " chromosome with attractivity " + chromosome.getAttractivity());
		
		genome.get(tier).sort((a, b) -> {
			return Double.compare(a.getKey().getAttractivity(), b.getKey().getAttractivity());
		});
		
		trim(tier);
		enumerate(tier);
		
	}

	private void trim(GenomeTier tier) {
		if(genome.get(tier).size() > Parameters.GENOME_SIZE) {
			Chromosome weakestChromosome = genome.get(tier).remove(0).getKey();
			
			System.out.println("Removed " + tier.name() + " chromosome with attractivity " + weakestChromosome.getAttractivity());
		}
	}

	private void enumerate(GenomeTier tier) {
		probabilitySum.put(tier, 0d);
		for(Entry<Chromosome, Double> chromosome : genome.get(tier)) {
			double currentSum = probabilitySum.get(tier);
			probabilitySum.put(tier, currentSum + chromosome.getKey().getAttractivity());
			chromosome.setValue(probabilitySum.get(tier));
		}
	}

	private Genotype getNextGenotype() {
		
		if(genome.get(GenomeTier.LOOKING_FOR).size() < Parameters.GENOME_SIZE)
			return null;

		if(genome.get(GenomeTier.OPTIMIZING).size() < Parameters.GENOME_SIZE)
			return createChild(GenomeTier.LOOKING_FOR);

		return createChild(GenomeTier.OPTIMIZING);
	}

	private Genotype createChild(GenomeTier tier) {
		Entry<Chromosome, Double> firstParent = getNextParent(tier);
		Entry<Chromosome, Double> secondParent;
		do
			secondParent = getNextParent(tier);
		while(firstParent == secondParent);
		
		return createChild(firstParent.getKey(), secondParent.getKey());
	}

	private Entry<Chromosome, Double> getNextParent(GenomeTier tier) {
		double index = ThreadLocalRandom.current().nextDouble(probabilitySum.get(tier));
		int loop = 0;
		for(Entry<Chromosome, Double> sequence : genome.get(tier)) {
			loop++;
			if(index < sequence.getValue()) {
				System.out.println("\t\t\t\t\t\tIndex: " + index + " sum: " + probabilitySum.get(tier) + " loop: " + loop);
				return sequence;
			}
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
