package com.rs;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class Quote {
    private String author;
    private String quote;

    public Quote() {
    }

    public Quote(String author, String quote) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Quote quote1 = (Quote) o;

        return new org.apache.commons.lang3.builder.EqualsBuilder()
                .append(author, quote1.author)
                .append(quote, quote1.quote)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new org.apache.commons.lang3.builder.HashCodeBuilder(17, 37)
                .append(author)
                .append(quote)
                .toHashCode();
    }
}
