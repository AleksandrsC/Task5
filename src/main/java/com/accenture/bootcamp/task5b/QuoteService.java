package com.accenture.bootcamp.task5b;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class QuoteService {
    private final AtomicLong counter = new AtomicLong();
    public Quote getRandomQuote(){
        Quote rv=new Quote("success",counter.incrementAndGet(),"This is a dummied-out version");

        return rv;
    }
}
