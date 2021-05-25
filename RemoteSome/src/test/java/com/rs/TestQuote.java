package com.rs;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class TestQuote {
    private String author;
    private String quote;

    public TestQuote() {
    }

    public TestQuote(String author, String quote) {
        this.author = author;
        this.quote = quote;
    }

    public String getAuthor() {
        return author;
    }

    public String getQuote() {
        return quote;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }
}
