package com.accenture.bootcamp.task5b;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;

@Service
public class QuoteService {

    private final ValueRepository valueRepository;
    private static final Logger log = LoggerFactory.getLogger(QuoteService.class);
    private final Random rng=new SecureRandom();

    @Autowired
    public QuoteService(ValueRepository valueRepository) {
        this.valueRepository = valueRepository;
    }

    public Quote getRandomQuote(){
        long randomIdx = getRandomIdx();
        try {
             return new Quote("success",valueRepository.findById(randomIdx).get());//crude, but should work
        }catch (Exception x){
            log.error("error getting random quote at index ["+randomIdx+"]",x);
            return new Quote("error", 0, x.getMessage());
        }
    }

    private long getRandomIdx() {
        long randomIdx=rng.nextLong(valueRepository.count());
        return randomIdx;
    }
}
