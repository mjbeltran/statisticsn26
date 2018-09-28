package com.n26.statistics.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Stack;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.n26.statistics.controller.MainController;
import com.n26.statistics.model.Statistics;
import com.n26.statistics.model.Transaction;
import com.n26.statistics.model.TransactionBean;
import com.n26.statistics.model.TransactionDto;
import com.n26.statistics.repository.TransactionStorage;
import com.n26.statistics.services.StatisticsService;
import com.n26.statistics.services.TransactionService;

@RunWith(SpringRunner.class)
@WebMvcTest(MainController.class)
public class MainControllerTest {

	private static final String URL = "http://localhost:8080/api/";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	TransactionStorage transactionStorage;

	@MockBean
	TransactionService transactionService;

	@MockBean
	StatisticsService statisticsService;

	@Autowired
	TransactionService transactionServiceTest;
	@Autowired
	StatisticsService statisticsServiceTest;

	TransactionDto transaction;

	@Before
	public void setUp() throws Exception {

		transaction = new TransactionDto();
		transaction.setAmount("12.3343");
		transaction.setTimestamp("2018-07-17T09:59:51.312Z");

	}

	@Test
	public void testAddValidTransaction() throws Exception {

		when(transactionService.addTransaction(any(Transaction.class))).thenReturn(true);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post(URL + "transactions").contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_JSON_UTF8).content(TestHelper.objectToJson(transaction)))
				.andReturn();

		int status = result.getResponse().getStatus();
		assertEquals("Response Status", HttpStatus.CREATED.value(), status);
		verify(transactionService).addTransaction(any(Transaction.class));

		int res = result.getResponse().getStatus();
		assertNotNull(res);
		assertEquals(201, res);

	}

	@Test
	public void testAddInValidTransaction() throws Exception {

		transaction.setTimestamp("2018-07-17T09:59:51.312Z");
		when(transactionService.addTransaction(any(Transaction.class))).thenReturn(false);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post(URL + "transactions").contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_JSON_UTF8).content(TestHelper.objectToJson(transaction)))
				.andReturn();

		int status = result.getResponse().getStatus();
		assertEquals("Response Status", HttpStatus.NO_CONTENT.value(), status);
		verify(transactionService).addTransaction(any(Transaction.class));

		int res = result.getResponse().getStatus();
		assertNotNull(res);
		assertEquals(204, res);

	}

	@Test
	public void testForTransactionStatisticsWithIn60Seconds() throws Exception {

		Statistics st = new Statistics();

		when(statisticsService.getStatisticsLastMinute()).thenReturn(st);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get(URL + "statistics").accept(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();
		int status = result.getResponse().getStatus();
		assertEquals("Response Status", HttpStatus.OK.value(), status);
	}

	@Test
	public void testForTransactionStatisticsLessThan60Seconds() throws Exception {
		Statistics st = new Statistics();

		when(statisticsService.getStatisticsLastMinute()).thenReturn(st);

		// execute
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get(URL + "statistics").accept(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();

		int status = result.getResponse().getStatus();
		assertEquals("Response Status", HttpStatus.OK.value(), status);
	}

	@Test
	public void testDeleteAllTransactions() throws Exception {

		Stack<TransactionBean> unfilteredStack = new Stack<>();

		Date d1 = new Date();
		Date d2 = new Date(d1.getTime() - 5 * 60 * 1000);

		TransactionBean transVO = new TransactionBean();
		transVO.setAmount(new BigDecimal("20.0"));
		transVO.setTimestamp(d2);

		unfilteredStack.push(transVO);

		Mockito.doNothing().when(transactionService).deleteAllTransactions();

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.delete(URL + "transactions").accept(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();

		int status = result.getResponse().getStatus();
		assertEquals("Response Status", HttpStatus.NO_CONTENT.value(), status);

		int res = result.getResponse().getStatus();
		assertNotNull(res);
		assertEquals(204, res);
	}

}
