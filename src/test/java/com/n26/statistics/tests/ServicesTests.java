package com.n26.statistics.tests;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Stack;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.n26.statistics.model.Statistics;
import com.n26.statistics.model.Transaction;
import com.n26.statistics.services.StatisticsService;
import com.n26.statistics.services.TransactionService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServicesTests {

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private StatisticsService statisticsService;

	@Test
	public void testForTransactionStatisticsWithIn60SecondsAA() throws Exception {

		Stack<Transaction> unfilteredStack = new Stack<>();

		Date d1 = new Date();

		Transaction trans1 = new Transaction();
		trans1.setAmount(new BigDecimal("10.0"));
		trans1.setTimestamp(d1.getTime());

		Transaction trans2 = new Transaction();
		trans2.setAmount(new BigDecimal("25.0"));
		trans2.setTimestamp(d1.getTime() + 4000);

		unfilteredStack.push(trans1);
		unfilteredStack.push(trans2);

		transactionService.addTransaction(trans1);
		transactionService.addTransaction(trans2);

		Statistics statsitics = statisticsService.getStatisticsLastMinute();
		assertNotNull(statsitics);
	}
}
