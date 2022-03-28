package com.accenture.bootcamp.task5a;

import com.accenture.bootcamp.task5b.Quote;
import com.accenture.bootcamp.task5b.QuoteController;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

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
		Quote rv=new Quote();
		rv=restTemplate.getForObject("http://localhost:" + port + "/", Quote.class);
		assertEquals("success",rv.getType());
	}

}
