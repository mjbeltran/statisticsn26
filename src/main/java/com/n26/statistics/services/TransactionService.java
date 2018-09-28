package com.n26.statistics.services;

import java.util.Stack;

import com.n26.statistics.model.Transaction;
import com.n26.statistics.model.TransactionBean;

public interface TransactionService {

	boolean addTransaction(Transaction transaction);
	
	Stack<TransactionBean> getAllTransactions();
	
	void deleteAllTransactions();
}
