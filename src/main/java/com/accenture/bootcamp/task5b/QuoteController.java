package com.accenture.bootcamp.task5b;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuoteController {
    private final QuoteService quoteService;
//TODO: proper request types
    @Autowired
    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping("/quote")
    public Quote quote() {
        return quoteService.getRandomQuote();
    }


    @PostMapping("/quoteById")
    public Quote quote(@RequestBody Long id) {
        return quoteService.getQuoteByID(id);
    }


    @PostMapping("/insertQuote")
    public void insertQuote(@RequestBody Quote quote){
        quoteService.editQuote(quote,true);
    }

    @PostMapping("/editQuote")
    public void editQuote(@RequestBody Quote quote){
        quoteService.editQuote(quote,false);
    }

    @PostMapping("/deleteQuote")
    public void deleteQuote(@RequestBody Long id){
        quoteService.deleteById(id);
    }



}
