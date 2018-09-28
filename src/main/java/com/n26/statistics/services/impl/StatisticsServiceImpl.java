package com.n26.statistics.services.impl;

import java.math.BigDecimal;
import java.util.DoubleSummaryStatistics;
import java.util.Stack;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.n26.statistics.model.Statistics;
import com.n26.statistics.model.TransactionBean;
import com.n26.statistics.services.StatisticsService;
import com.n26.statistics.services.TransactionService;
import com.n26.statistics.util.DateHelper;

@Service
public class StatisticsServiceImpl implements StatisticsService {

	@Autowired
	private TransactionService transactionService;

	@Override
	public Statistics getStatisticsLastMinute() {

		Stack<BigDecimal> transAmtInStack = getTransactionLastMinute(transactionService.getAllTransactions());

		Statistics transStatistics = getTransactionsValues(transAmtInStack);

		return transStatistics;
	}

	public static Stack<BigDecimal> getTransactionLastMinute(Stack<TransactionBean> unFilteredStack) {

		Stack<BigDecimal> filteredStack = new Stack<>();

		if (unFilteredStack != null && unFilteredStack.size() > 0) {

			int index = unFilteredStack.size() - 1;
			for (int i = index; i >= 0; i--) {
				TransactionBean transactionSaved = unFilteredStack.get(index);
				if (transactionSaved.getTimestamp().after(DateHelper.getTimeLessMinute())) {
					filteredStack.push(transactionSaved.getAmount());
				} else {
					break;
				}
			}
		}

		return filteredStack;
	}

	public static Statistics getTransactionsValues(Stack<BigDecimal> statsStack) {

		Statistics stats = new Statistics();

		if (statsStack != null && statsStack.size() > 0) {

			DoubleSummaryStatistics summaryStatistics = statsStack.stream()
					.collect(Collectors.summarizingDouble(BigDecimal::doubleValue));
			stats.setMax(summaryStatistics.getMax());
			stats.setMin(summaryStatistics.getMin());
			stats.setAvg(summaryStatistics.getAverage());
			stats.setSum(summaryStatistics.getSum());
			stats.setCount(summaryStatistics.getCount());

		}
		return stats;
	}

}
