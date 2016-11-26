package com.baiju.demo;

import org.apache.log4j.BasicConfigurator;

import com.baiju.demo.core.Simulator;


public class DemoMain {
	
	public static void main (String[] args) {
		BasicConfigurator.configure();
		Simulator simulator = new Simulator();
		simulator.simulate();
	}

}
