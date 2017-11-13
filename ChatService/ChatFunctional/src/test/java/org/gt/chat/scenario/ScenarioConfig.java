package org.gt.chat.scenario;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class ScenarioConfig {
    private final Config config;

    public ScenarioConfig() {
        config = ConfigFactory.load();
    }

    public String getString(ConfigVariables propertyName) {
        return config.getString(propertyName.getName());
    }
}
