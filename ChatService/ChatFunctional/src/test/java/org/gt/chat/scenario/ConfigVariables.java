package org.gt.chat.scenario;

import lombok.Getter;

@Getter
public enum ConfigVariables {
    HOST("host"),
    DATABASE_HOST("database.host"),
    DATABASE_PORT("database.port"),
    DATABASE_NAME("database.name"),
    DATABASE_COLLECTION("database.collection");

    private String name;

    ConfigVariables(String propertyName) {
        this.name = propertyName;
    }
}
