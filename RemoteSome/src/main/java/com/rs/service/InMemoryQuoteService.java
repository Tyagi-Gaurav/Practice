package com.rs.service;

import com.rs.Quote;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InMemoryQuoteService implements QuoteService {
    private List<Quote> quotes = new ArrayList<>();

    @Override
    public void add(Quote quote) {
        quotes.add(quote);
    }

    @Override
    public List<Quote> getQuotes() {
        return quotes;
    }
}
