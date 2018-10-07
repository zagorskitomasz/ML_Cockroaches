package com.zagorskidev.cockroaches.population;

public class SuperSequence extends GeneticSequence {

	public SuperSequence(Sequence propagatedSequence) {
		super(propagatedSequence.getMovements());
	}

	@Override
	public int getLength() {
		return super.getLength() * 2;
	}
}
