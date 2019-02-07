package org.gt.streams;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MetaData {
    @JsonProperty
    String name;
    @JsonProperty
    Long amount = 0L;
    @JsonProperty
    private String time;

    public MetaData() {
    }

    public MetaData(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "{}";
    }

    public void next(Long value, LocalDateTime time) {
        amount = value;
        this.time = time.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public Long getAmount() {
        return amount;
    }

    public String getTime() {
        return time;
    }
}