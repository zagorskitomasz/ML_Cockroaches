package com.zagorskidev.cockroaches.population;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.zagorskidev.cockroaches.population.generators.Circle;
import com.zagorskidev.cockroaches.population.generators.Down;
import com.zagorskidev.cockroaches.population.generators.GoAndBack;
import com.zagorskidev.cockroaches.population.generators.LShape;
import com.zagorskidev.cockroaches.population.generators.Left;
import com.zagorskidev.cockroaches.population.generators.Right;
import com.zagorskidev.cockroaches.population.generators.SequenceGenerator;
import com.zagorskidev.cockroaches.population.generators.SpiralLeft;
import com.zagorskidev.cockroaches.population.generators.SpiralRight;
import com.zagorskidev.cockroaches.population.generators.Up;
import com.zagorskidev.cockroaches.population.generators.Zigzag;
import com.zagorskidev.cockroaches.system.Movement;

public class BasicSequence extends GeneticSequence{
	
	public BasicSequence() {
		super(generate());
	}

	private static List<Movement> generate() {
		SequenceGenerator generator = null;
		
		switch(ThreadLocalRandom.current().nextInt(10)) {
		case 0:
			generator = new Up();
			break;
		case 1:
			generator = new Left();
			break;
		case 2:
			generator = new Right();
			break;
		case 3:
			generator = new Down();
			break;
		case 4:
			generator = new Circle();
			break;
		case 5:
			generator = new SpiralLeft();
			break;
		case 6:
			generator = new GoAndBack();
			break;
		case 7:
			generator = new LShape();
			break;
		case 8:
			generator = new Zigzag();
			break;
		case 9:
			generator = new SpiralRight();
			break;
		}
		return generator.generate();
	}

	@Override
	public void addMovement(Movement movement) {
		throw new UnsupportedOperationException("Basic sequence can't be programmed");
	}

	@Override
	public void mutate() {
		throw new UnsupportedOperationException("Basic sequence can't be mutated");
	}
}
