package com.n26.statistics.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.n26.statistics.model.Statistics;
import com.n26.statistics.model.Transaction;
import com.n26.statistics.model.TransactionDto;
import com.n26.statistics.services.StatisticsService;
import com.n26.statistics.services.TransactionService;
import com.n26.statistics.util.DateHelper;

@RestController
@RequestMapping("/api")
public class MainController {

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private StatisticsService statisticsService;

	@PostMapping("/transactions")
	public ResponseEntity<Void> saveTransaction(@RequestBody TransactionDto transactionDto) throws ParseException {

		Transaction transaction = setAndValidateTransactionIn(transactionDto);

		if (transaction.getTimestamp() > DateHelper.getCurrentTimeMiliseconds()) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
		}

		boolean saveTransaction = transactionService.addTransaction(transaction);
		if (saveTransaction) {
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

		}
	}

	@GetMapping("/statistics")
	public Statistics getStatistics() {

		return statisticsService.getStatisticsLastMinute();
	}

	@DeleteMapping("/transactions")
	public ResponseEntity<Void> deleteAllTransactions() {
		transactionService.deleteAllTransactions();
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

	}

	@ExceptionHandler(IllegalArgumentException.class)
	public final ResponseEntity<Void> handleParseException(IllegalArgumentException ex) {
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
	}

	private Transaction setAndValidateTransactionIn(TransactionDto transactionDto) throws ParseException {
		BigDecimal amount = new BigDecimal(transactionDto.getAmount());

		Instant instantDateTransactionIn = Instant.parse(transactionDto.getTimestamp());
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setTimestamp(instantDateTransactionIn.getEpochSecond() * 1000);
		return transaction;
	}

}
