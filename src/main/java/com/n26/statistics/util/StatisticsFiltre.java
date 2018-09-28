package com.n26.statistics.util;
//package com.n26.code.challenge.util;
//
//import java.math.BigDecimal;
//import java.util.Date;
//import java.util.DoubleSummaryStatistics;
//import java.util.Stack;
//import java.util.stream.Collectors;
//
//import com.n26.code.challenge.bean.Statistics;
//import com.n26.code.challenge.bean.TransactionBean;
//
//public class StatisticsFiltre {
//
//	
//	public static Stack<BigDecimal> getTransactionWithInTimeFrame(Stack<TransactionBean> unFilteredStack) {
//
//		Stack<BigDecimal> filteredStack = new Stack<>();
//
//		if (unFilteredStack != null && unFilteredStack.size() > 0) {
//
//			// filter the stack which has transaction within 60 seconds
//			for (TransactionBean transactionSaved : unFilteredStack) {
//
//				if (transactionSaved.getTimestamp().after(getCurrentTimeLessThanMinute())) {
////				if(DateHelper.getCurrentTimeMiliseconds() < transactionSaved.getTimestamp().getTime() ) {
//
//					filteredStack.push(transactionSaved.getAmount());
//
//				} else {
//					break;
//				}
//			}
//		}
//
//		return filteredStack;
//	}
//
//	// /**
//	// * Method will return current time with less than 1 minute
//	// *
//	// * @return startTime
//	// */
////	public static Date getCurrentTimeLessThanMinute() {
////
////		// get the current time
////		Date now = new Date();
////
////		// minus 1 minute i.e 60 seconds
////		Date startTime = new Date(now.getTime() - (1 * 60 * 1000));
////
////		return startTime;
////	}
//
//	public static Statistics getTransactionsValues(Stack<BigDecimal> statsStack) {
//
//		Statistics stats = new Statistics();
//
//		if (statsStack != null && statsStack.size() > 0) {
//
//			DoubleSummaryStatistics summaryStatistics = statsStack.stream()
//					.collect(Collectors.summarizingDouble(BigDecimal::doubleValue));
//			stats.setMax(summaryStatistics.getMax());
//			stats.setMin(summaryStatistics.getMin());
//			stats.setAvg(summaryStatistics.getAverage());
//			stats.setSum(summaryStatistics.getSum());
//			stats.setCount(summaryStatistics.getCount());
//
//		}
//		return stats;
//	}
//}
