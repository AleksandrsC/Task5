package com.accenture.bootcamp.task5b;


import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuoteController {
    private final AtomicLong counter = new AtomicLong();
    @GetMapping("/quote")
    public Quote quote() {
        Quote rv=new Quote("success",counter.incrementAndGet(),"This is a dummied-out version");

        return rv;
    }

}
