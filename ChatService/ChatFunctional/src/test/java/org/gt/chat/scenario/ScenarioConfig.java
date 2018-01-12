package org.gt.chat.scenario;

import com.google.inject.Singleton;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

@Singleton
public class ScenarioConfig {
    private final Config config;

    public ScenarioConfig() {
        config = ConfigFactory.load("functionalTestApplication.conf");
    }

    public String getString(ConfigVariables propertyName) {
        return config.getString(propertyName.getName());
    }

    public int getInt(ConfigVariables propertyName) {
        return config.getInt(propertyName.getName());
    }
}
