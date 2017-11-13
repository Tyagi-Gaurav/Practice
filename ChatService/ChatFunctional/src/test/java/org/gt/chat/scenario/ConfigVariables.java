package org.gt.chat.scenario;

import lombok.Getter;

@Getter
public enum ConfigVariables {
    HOST("host");

    private String name;

    ConfigVariables(String propertyName) {
        this.name = propertyName;
    }
}
