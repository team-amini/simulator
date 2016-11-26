package com.baiju.demo.messages;



import org.joda.time.DateTime;

import com.baiju.demo.core.Simulator.Point;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Transaction {
	private String action;
	private int from;
	private int to;
	private float amount;
	private DateTime transactiontime;
	private Point fromPoint;
	private Point toPoint;

}
