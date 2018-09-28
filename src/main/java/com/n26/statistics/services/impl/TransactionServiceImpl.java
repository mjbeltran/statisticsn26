package com.n26.statistics.services.impl;

import java.util.Stack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.n26.statistics.model.Transaction;
import com.n26.statistics.model.TransactionBean;
import com.n26.statistics.repository.TransactionStorage;
import com.n26.statistics.services.TransactionService;
import com.n26.statistics.util.DateHelper;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionStorage transactionStorage;

	public boolean addTransaction(Transaction transaction) {

		long currentTime = DateHelper.getCurrentTimeMiliseconds();

		if (currentTime < DateHelper.getTimeLongFromTimeStampMiliseconds(transaction.getTimestamp())
				|| currentTime > DateHelper.getTimeLongFromTimeStampMiliseconds(transaction.getTimestamp())
						+ DateHelper.EXPIRED_DUR_TRANSACTION) {
			return false;
		}

		return transactionStorage.addTransaction(transaction);
	}

	public Stack<TransactionBean> getAllTransactions() {
		return transactionStorage.getAllTransactions();
	}

	@Override
	public void deleteAllTransactions() {
		transactionStorage.deleteAllTransactions();;

	}
}
