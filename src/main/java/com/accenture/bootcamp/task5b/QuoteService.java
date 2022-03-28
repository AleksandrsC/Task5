package com.accenture.bootcamp.task5b;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuoteService {
    private final ValueRepository valueRepository;
    private static final Logger log = LoggerFactory.getLogger(QuoteService.class);

    @Autowired
    public QuoteService(ValueRepository valueRepository) {
        this.valueRepository = valueRepository;
    }

    public Quote getRandomQuote(){
        try {
            Quote rv=new Quote("success",valueRepository.getReferenceById(Math.round(Math.random()*valueRepository.count())));//crude, but should work
            return rv;
        }catch (Exception x){
            log.error("error getting random quote",x);
            return new Quote("error", 0, x.getMessage());
        }
    }
}
