package com.baiju.demo.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

import com.baiju.demo.actors.PayeeAgent;
import com.baiju.demo.actors.Router;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

@Slf4j
public class Simulator {

	private final ActorSystem actorSystem = ActorSystem.create();
	public static ActorRef[] totalParticipants = new ActorRef[Properties.numTotalParticipants];
	public static Point[] totalParticipantsGeoPoints = new Point[Properties.numTotalParticipants];
	public ArrayList<Point> geopoints;
	final static Random random = new Random();

	@SneakyThrows
	public void simulate() {
		log.info("starting simulation");
		loadGeolocations();
		createRequestAgents();
		createRouter();
		Thread.sleep(120000);
		log.info("shutting simulation");
		actorSystem.shutdown();
	}

	private void createRequestAgents() {

		String name;
		Props props;
		for (int i = 0; i < Properties.numTotalParticipants; i++) {
			name = "participant" + i;
			Point randomPoint = geopoints.get(randomInt(0, geopoints.size()-1));
			props = Props.create(PayeeAgent.class,i+1,randomPoint);
			totalParticipants[i] = actorSystem.actorOf(props, name);
			totalParticipantsGeoPoints[i] = randomPoint;
		}
	}

	private void createRouter() {
		String name = "router";
		Props props = Props.create(Router.class);
		actorSystem.actorOf(props, name);
	}
	
	@SneakyThrows
	private void loadGeolocations() {
		BufferedReader reader = new BufferedReader( new FileReader(Properties.geofilename));
		String line = "";
        String cvsSplitBy = "\t";
        geopoints = new ArrayList<Simulator.Point>();
        int linenumber = 0;
		while ((line = reader.readLine()) != null) {
			if (linenumber == 0) {
				linenumber = linenumber+1;
				continue;
			}
			linenumber = linenumber+1;
            // use comma as separator
            String[] data = line.split(cvsSplitBy);
            Point point = new Point(data[5],data[6]);
            geopoints.add(point);
        }
	}
	
	@AllArgsConstructor
	@Data
	public class Point {
		String lattitude;
		String longitude;
	}

	/**
	 * Return a random number in range {@code [min, max]}
	 */
	public static int randomInt(int min, int max) {
		return min + random.nextInt(max - min) + 1;
	}

}
