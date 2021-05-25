package com.rs.config;

import com.rs.service.InMemoryQuoteService;
import com.rs.service.QuoteService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    public QuoteService quoteService() {
        return new InMemoryQuoteService();
    }
}
