package com.rs;

import com.rs.service.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class QuoteResource {
    @Autowired
    private QuoteService quoteService;

    @PostMapping(path = "/api/v1/quote",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity addQuote(@RequestBody Quote quote) {
        quoteService.add(quote);

        return ResponseEntity.status(204).build();
    }
}
