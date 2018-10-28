package com.zagorskidev.cockroaches;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.zagorskidev.cockroaches.graphics.Drawer;
import com.zagorskidev.cockroaches.input.EnvironmentEventsHandler;
import com.zagorskidev.cockroaches.population.Genome;
import com.zagorskidev.cockroaches.population.Population;
import com.zagorskidev.cockroaches.timers.BirthTimer;
import com.zagorskidev.cockroaches.timers.MoveTimer;
import com.zagorskidev.cockroaches.timers.Timer;

public class Cockroaches extends ApplicationAdapter {
	
	private final boolean isDesktop;
	private boolean started;
	
	private List<Timer> timers;
	private Drawer drawer;
	
	private Population cockroaches;
	
	public Cockroaches(boolean isDesktop) {
		this.isDesktop = isDesktop;
	}
	
	@Override
	public void create () {
		initializeCockroaches();
		initializeEnvironment();
		initializeGameEngine();
	}

	private void initializeCockroaches() {
		Genome genome = new Genome();
		cockroaches = new Population(genome);
	}
	
	private void initializeEnvironment() {
		timers = new LinkedList<>();
		timers.add(new MoveTimer(cockroaches));
		timers.add(new BirthTimer(cockroaches));
	}

	private void initializeGameEngine() {
		drawer = new Drawer(cockroaches,isDesktop);
		Gdx.input.setInputProcessor(new EnvironmentEventsHandler(this));
	}

	@Override
	public void render () {
		if(started) {
			for(Timer timer : timers)
				timer.checkAndPerform();
		}
		drawer.draw();
	}
	
	@Override
	public void dispose () {
		drawer.dispose();
	}
	
	public void start() {
		started = true;
	}
}
