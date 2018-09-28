package com.n26.statistics.util;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Date;

public class DateHelper {

	public static final long EXPIRED_DUR_TRANSACTION = 60000;

	 public static long getCurrentTimeSeconds() {
	 long nowTime = Instant.now(Clock.systemUTC()).getEpochSecond();
	 return nowTime;
	 }

	public static long getCurrentTimeMiliseconds() {
		long nowTime = Instant.now(Clock.systemUTC()).getEpochSecond() * 1000;
//		Date now = new Date();
//		Date nowTime = new Date(now.getTime());
		return nowTime;
	}

	// public static long getTimeLongFromTimeStampSeconds(long timeStamp) {
	//
	// long timeTransactionIn =
	// Instant.ofEpochMilli(timeStamp).atOffset(ZoneOffset.UTC).toEpochSecond();
	// return timeTransactionIn;
	// }

	public static long getTimeLongFromTimeStampMiliseconds(long timeStamp) {

		long timeTransactionIn = Instant.ofEpochMilli(timeStamp).atOffset(ZoneOffset.UTC).toEpochSecond() * 1000;
		return timeTransactionIn;
	}

	public static Date getTimeLessMinute() {
		Date now = new Date();
		Date startTime = new Date(now.getTime() - (1 * 60 * 1000));
		return startTime;
	}
}
