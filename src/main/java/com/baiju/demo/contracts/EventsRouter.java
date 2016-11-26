package com.baiju.demo.contracts;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

@Slf4j
public class EventsRouter {

	public static void main (String[] args) {
		EventsRouter ev = new EventsRouter();
		ev.run();
	}
	
	@SneakyThrows
	public void run () {
		Web3j web3 = Web3j.build(new HttpService("http://amini.canadaeast.cloudapp.azure.com:8545/"));  // defaults to http://localhost:8545/
		Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().sendAsync().get();
		String clientVersion = web3ClientVersion.getWeb3ClientVersion();
		log.info(clientVersion);
	}
	
}
