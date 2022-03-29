package com.accenture.bootcamp.task5b;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.owasp.validator.html.*;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

@Service
public class QuoteService {
    private Policy quoteHTMLPolicy;//plain text only

    private final ValueRepository valueRepository;
    private static final Logger log = LoggerFactory.getLogger(QuoteService.class);
    private final Random rng=new SecureRandom();

    @Autowired
    public QuoteService(ValueRepository valueRepository) {

        this.valueRepository = valueRepository;
        try {
            quoteHTMLPolicy = Policy.getInstance("src/main/resources/antisamy-tinymce.xml");
        } catch (PolicyException e) {
            log.error("failed to create antisamy policy. ", e );
        }
    }

    public Quote getRandomQuote(){
        long randomIdx = getRandomIdx();
        try {
             return new Quote("success",valueRepository.findById(randomIdx).get());//crude, but should work
        }catch (Exception x){
            log.error("error getting random quote at index ["+randomIdx+"]",x);
            return new Quote("error", randomIdx, x.getMessage());
        }
    }

    private long getRandomIdx() {
        //Java 11 doesn't have nextLong(min,max) this is a bit crude, but it works for low-importance app.
        return Math.abs(rng.nextLong())%valueRepository.count();
    }

    public void insertQuote(Quote quote) {
        Value v=quote.getValue();
        CleanResults scan = null;
        try {
            scan = new AntiSamy(quoteHTMLPolicy).scan(v.getQuote());
        } catch (Exception e) {
            log.error("input sanitization failed, refusing DB update.", e);
            return;
        }
        if(scan.getNumberOfErrors()>0) {
            StringBuilder sb = new StringBuilder("errors \n:");
            for (String s : scan.getErrorMessages()) sb.append(s);
            log.info(sb.toString());
        }
        v.setQuote(scan.getCleanHTML());
        v.setId(null);
        valueRepository.save(v);
    }
}
