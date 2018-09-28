package com.n26.statistics.tests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.n26.statistics.services.StatisticsService;
import com.n26.statistics.services.TransactionService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StatisticsAppTest {

    @Autowired
    private TransactionService transactionService;
    
    @Autowired
	private StatisticsService statisticsService;

	@Test
	public void contextLoadsTransactionService() {
        Assert.assertNotNull("transactionService can not be null", transactionService);
	}

	public void contextLoadsStatisticsService() {
        Assert.assertNotNull("transactionService can not be null", statisticsService);
	}
}
