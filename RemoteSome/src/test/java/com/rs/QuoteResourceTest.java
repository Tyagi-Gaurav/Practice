package com.rs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rs.service.InMemoryQuoteService;
import com.rs.service.QuoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@EnableWebMvc
@SpringBootTest(classes = {QuoteResource.class, InMemoryQuoteService.class})
public class QuoteResourceTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QuoteService quoteService;

    public void inValidBasicAuthShouldReturn401() throws Exception {
        String encodedBasicAuth = "dXNlcjE6dXNlcjFQYXN";

        //when
        mockMvc.perform(post("/api/v1/quote")
                .header("Authorization", "Basic " + encodedBasicAuth)
                .content("")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(401));
    }

    @Test
    public void addQuotesForAGivenAuthor() throws Exception {
        //given
        Quote seneca = new Quote("Seneca", "As long as you live, keep learning how to live");

        //when
        mockMvc.perform(post("/api/v1/quote")
                .content(asJsonString(seneca))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(204));

        //then
        assertThat(quoteService.getQuotes()).contains(seneca);
    }

    private static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
