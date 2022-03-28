package com.accenture.bootcamp.task5b;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuoteServiceTest {
    @Autowired
    private QuoteService quoteService;
    @Test
    void contextLoads() {
        assertNotNull(quoteService);
    }

    @Test
    void hundredRequests(){
        for (int i=0;i<100;i++)
            Assertions.assertTrue(quoteService.getRandomQuote().getType() == "success");
    }
}