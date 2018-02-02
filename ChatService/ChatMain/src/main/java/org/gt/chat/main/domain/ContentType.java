package org.gt.chat.main.domain;

import lombok.Getter;

@Getter
public enum ContentType {
    APPLICATION_JSON("application/json"),
    TEXT_PLAIN_UTF8("text/plain-utf8"),
    APPLICATION_OCTET_STREAM("application/octet-stream");

    private String value;

    ContentType(String value) {
        this.value = value;
    }
}