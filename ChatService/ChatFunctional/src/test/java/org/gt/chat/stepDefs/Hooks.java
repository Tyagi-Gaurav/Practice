package org.gt.chat.stepDefs;

import com.google.inject.Inject;
import cucumber.api.java.Before;
import cucumber.runtime.java.guice.ScenarioScoped;

@ScenarioScoped
public class Hooks {
    private ChatMainApplcationController chatMainApplcationController;

    @Inject
    public Hooks(ChatMainApplcationController chatMainApplcationController) {
        this.chatMainApplcationController = chatMainApplcationController;
    }

    @Before
    public void beforeAll() throws Exception {
        chatMainApplcationController.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
           chatMainApplcationController.stop();
        }));
    }
}
