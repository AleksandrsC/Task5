package com.accenture.bootcamp.task5b;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class QuoteServiceTest {
    private static final Logger log = LoggerFactory.getLogger(QuoteServiceTest.class);
    @Autowired
    private QuoteService quoteService;
    @Test
    public void contextLoads() {
        assertNotNull(quoteService);
    }

    @Test
    public void hundredRequests(){
        for (int i=0;i<100;i++)
            Assertions.assertTrue(quoteService.getRandomQuote().getType() == "success");
    }
}