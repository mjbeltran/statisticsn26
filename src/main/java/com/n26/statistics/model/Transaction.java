package com.n26.statistics.model;

import java.math.BigDecimal;

public class Transaction {

	private BigDecimal amount = new BigDecimal("0.0");
	private Long timestamp ;
	
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	
	
}
