package org.gt.chat;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"classpath:features/*.feature"},
        plugin = {"pretty", "html:target/cucumber-html-report"},
        glue="org.gt.chat"
)
public class RunCukesTest {
}
