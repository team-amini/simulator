package com.baiju.demo.actors;

import java.io.BufferedWriter;
import java.io.FileWriter;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import com.baiju.demo.messages.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import akka.actor.UntypedActor;

@Slf4j
public class Router extends UntypedActor {
	
	ObjectMapper mapper = new ObjectMapper();
	
	BufferedWriter writer;
	
	@SneakyThrows
	public Router() {
		writer = new BufferedWriter( new FileWriter( "/tmp/payment-sim-data"));
		mapper.registerModule(new JodaModule());
	}

	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof Transaction) {
			log.info("received transaction "
					+ ((Transaction) message).toString());
			writer.write(mapper.writeValueAsString((Transaction) message)+"\n");
		} else {
			unhandled(message);
		}
	}
	
	@Override
	@SneakyThrows
	public void postStop() {
		writer.flush();
		writer.close();
	}

}
