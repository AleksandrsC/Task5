package com.accenture.bootcamp.task5a;

import com.accenture.bootcamp.task5b.Quote;
import com.accenture.bootcamp.task5b.QuoteController;
import com.accenture.bootcamp.task5b.QuoteService;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {
	private static final Logger log = LoggerFactory.getLogger(DemoApplicationTests.class);

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private QuoteController quoteController;

	@Test
	void contextLoads() {
		assertNotNull(quoteController);
	}

	@Test
	void simpleRequest(){
		Quote rv=restTemplate.getForObject("http://localhost:" + port + "/quote", Quote.class);
		assertEquals ("success",rv.getType());
	}

}
