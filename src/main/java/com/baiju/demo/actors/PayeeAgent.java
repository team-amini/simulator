package com.baiju.demo.actors;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;

import com.baiju.demo.core.Properties;
import com.baiju.demo.core.Simulator;
import com.baiju.demo.core.Simulator.Point;
import com.baiju.demo.messages.Transaction;

import lombok.val;
import lombok.extern.slf4j.Slf4j;
import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.UntypedActor;

@Slf4j
public class PayeeAgent extends UntypedActor {
	
	int payerId;
	String payerName;
	int minSleep = 1000;
	int maxSleep = 10000;
	Point geopoint;
	
	
	
	public PayeeAgent(int id, Point geopoint) {
		this.payerId = id;
		this.geopoint = geopoint;
		//this.payerName = name;
	}
	
	@Override
	public void preStart() {
		getContext()
				.system()
				.scheduler()
				.scheduleOnce(Duration.create(500, TimeUnit.MILLISECONDS),
						getSelf(), "tick", getContext().dispatcher(), null);
	}
	
	// override postRestart so we don't call preStart and schedule a new message
	@Override
	public void postRestart(Throwable reason) {
	}


	@Override
	public void onReceive(Object message) throws Exception {
		if (message.equals("tick")) {
			// send another periodic tick after the specified delay
			getContext()
					.system()
					.scheduler()
					.scheduleOnce(Duration.create(Simulator.randomInt(minSleep,maxSleep), TimeUnit.MILLISECONDS),
							getSelf(), "tick", getContext().dispatcher(), null);
			onReceiveTick();
		} else {
			unhandled(message);
		}	
	}

	private void onReceiveTick() {
		// generate and publish request
		val request = generateTransaction();
		publish(request);
	}
	
	private Transaction generateTransaction() {
		String action = Properties.ACTIONS[0];
		int payeeId = getPayeeId();
		return new Transaction(action, this.payerId,payeeId,getAmount(), DateTime.now(),this.geopoint,Simulator.totalParticipantsGeoPoints[payeeId]);
	}
	
	// randomly pick someone to pay
	private int getPayeeId () {
		ActorRef[] participants = Simulator.totalParticipants;
		return Simulator.randomInt(0, participants.length-1);
	}
	
	private int getAmount() {
		return Simulator.randomInt(100,1000);
	}
	
	private void publish(Transaction transaction) {
		log.debug("Transaction sent");
		context().actorSelection("/user/router")
				.tell(transaction, getSelf());
	}

	
}
