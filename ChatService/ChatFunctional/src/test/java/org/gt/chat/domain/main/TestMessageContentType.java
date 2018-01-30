package org.gt.chat.domain.main;

import lombok.Getter;

@Getter
public enum TestMessageContentType {
    APPLICATION_JSON("application/json"),
    TEXT_PLAIN_UTF8("text/plain-utf8"),
    APPLICATION_OCTET_STREAM("application/octet-stream");

    private String value;

    TestMessageContentType(String value) {
        this.value = value;
    }
}
