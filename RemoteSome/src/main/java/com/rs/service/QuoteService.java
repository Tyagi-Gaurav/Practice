package com.rs.service;

import com.rs.Quote;

import java.util.List;

public interface QuoteService {
    void add(Quote quote);

    List<Quote> getQuotes();
}
