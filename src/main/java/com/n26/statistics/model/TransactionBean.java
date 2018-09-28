package com.n26.statistics.model;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionBean {
	
	private BigDecimal amount = new BigDecimal("0.0");
	private Date timestamp ;
	
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	
}
