package com.zagorskidev.cockroaches.population.generators;

import java.util.List;

import com.zagorskidev.cockroaches.system.Movement;

public interface SequenceGenerator {

	public List<Movement> generate();
}
