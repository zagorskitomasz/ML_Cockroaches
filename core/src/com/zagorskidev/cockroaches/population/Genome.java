package com.zagorskidev.cockroaches.population;

import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

import com.google.common.collect.Multiset;
import com.google.common.collect.TreeMultiset;
import com.zagorskidev.cockroaches.system.Parameters;

public class Genome {
	
	private Map<GenomeTier, Multiset<Entry<Chromosome, Double>>> genome;
	private Map<GenomeTier, Double> probabilitySum;
	
	public Genome() {
		
		genome = new ConcurrentHashMap<>();
		genome.put(GenomeTier.LOOKING_FOR, createMultiset());
		genome.put(GenomeTier.OPTIMIZING, createMultiset());
		
		probabilitySum = new ConcurrentHashMap<>();
		probabilitySum.put(GenomeTier.LOOKING_FOR, 0d);
		probabilitySum.put(GenomeTier.OPTIMIZING, 0d);
	}

	private Multiset<Entry<Chromosome, Double>> createMultiset() {
		
		return TreeMultiset.create((a, b) -> {
			return Double.compare(a.getKey().getAttractivity(), b.getKey().getAttractivity());
		});
	}

	public synchronized void addChromosome(Chromosome chromosome) {
		GenomeTier tier = chromosome.getTier();
			
		genome.get(tier).add(new SimpleEntry<>(chromosome, 0d));
		trim(tier);
		enumerate(tier);
		
		System.out.println("Added " + tier.name() + " chromosome with attractivity " + chromosome.getAttractivity());
	}

	private void trim(GenomeTier tier) {
		if(genome.get(tier).size() > Parameters.GENOME_SIZE) {
			Entry<Chromosome, Double> weakestChromosome = genome.get(tier).iterator().next();
			genome.get(tier).remove(weakestChromosome);
			
			System.out.println("Removed " + tier.name() + " chromosome with attractivity " + weakestChromosome.getKey().getAttractivity());
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

	public synchronized Genotype getNextGenotype() {
		
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
		
		mutate(firstParent.getKey());
		mutate(secondParent.getKey());
		
		return createChild(firstParent.getKey(), secondParent.getKey());
	}

	private void mutate(Chromosome chromosome) {
		chromosome.mutate();
	}

	private Entry<Chromosome, Double> getNextParent(GenomeTier tier) {
		double index = ThreadLocalRandom.current().nextDouble(probabilitySum.get(tier));
		int loop = 0;
		for(Entry<Chromosome, Double> sequence : (Collection<Entry<Chromosome, Double>>)(genome.get(tier))) {
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
