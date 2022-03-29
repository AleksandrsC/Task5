package com.accenture.bootcamp.task5b;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.owasp.validator.html.*;

import java.security.SecureRandom;
import java.util.Optional;
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

    private String sanitize(String qv) throws ScanException, PolicyException {
        CleanResults scan = new AntiSamy(quoteHTMLPolicy).scan(qv);
        if(scan.getNumberOfErrors()>0) {
            StringBuilder sb = new StringBuilder("errors \n:");
            for (String s : scan.getErrorMessages()) sb.append(s);
            log.info(sb.toString());
        }
        return scan.getCleanHTML();

    }

    /**
     *
     * @param id id of the quote
     * @return the quote or ["failure",null] if error
     */
    public Quote getQuoteByID(long id){
        Quote rv=new Quote("error",null);
        Optional<Value> result=valueRepository.findById(id);
        if(result.isPresent()){
            rv.setType("success");
            rv.setValue(result.get());
        }
        return rv;
    }

    public void deleteById(Long id){
        try {
            valueRepository.deleteById(id);
        }catch(Exception x){
            log.error("error deleting ID:"+id, x);
        }
    }
    /**
     * insert/update method
     * @param quote quote to edit
     * @param isInsert if true the quote is added, even if there's a quote like that already, if false, the quote with Id provided is replaced with new quote, nothing happens if there's no quote with this id.
     */
    public void editQuote(Quote quote, boolean isInsert) {
        Value v=quote.getValue();
        try {
            v.setQuote(sanitize(v.getQuote()));
        }catch(Exception x){
            log.error("failed to sanitize input, aborting update");
            return;
        }

        if(isInsert) {
            v.setId(null);
        }else if(!valueRepository.existsById(v.getId())){
            log.error("attempt to update nonexistent ID:"+v.getId());
            return;
        }
        valueRepository.save(v);
    }
}
