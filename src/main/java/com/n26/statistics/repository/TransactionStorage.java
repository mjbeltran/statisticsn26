package com.n26.statistics.repository;

import java.util.Date;
import java.util.Stack;

import org.springframework.stereotype.Component;

import com.n26.statistics.model.Transaction;
import com.n26.statistics.model.TransactionBean;
import com.n26.statistics.util.DateHelper;

@Component
public class TransactionStorage {

	Stack<TransactionBean> transDetailsStack = new Stack<>();

	public boolean addTransaction(Transaction transactionToSave) {

		boolean transactionStored = false;
		synchronized (this) {
			if (transactionToSave != null && transactionToSave.getTimestamp() > 0) {

//				long nowTime = DateHelper.getCurrentTimeMiliseconds();
//				long timeTransactionIn = transactionToSave.getTimestamp();
				
				//Convert epoch timestamp to java date object
				Date transDate = new Date(transactionToSave.getTimestamp());
				
				//get the currenttime less than 60 seconds
				Date startTime = DateHelper.getTimeLessMinute();

				if(transDate.after(startTime)){

					TransactionBean transactionBean = new TransactionBean();

					transactionBean.setAmount(transactionToSave.getAmount());
					transactionBean.setTimestamp(new Date(transactionToSave.getTimestamp()));

					transDetailsStack.push(transactionBean);

					transactionStored = true;
				}
			}
		}
		return transactionStored;
	}

	public Stack<TransactionBean> getAllTransactions() {
		return transDetailsStack;
	}

	public void deleteAllTransactions() {
		transDetailsStack.removeAllElements();
	}
}
