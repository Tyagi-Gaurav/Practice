package org.gt.chat.stepDefs;

import com.google.inject.Inject;
import cucumber.api.java.Before;
import cucumber.runtime.java.guice.ScenarioScoped;

@ScenarioScoped
public class Hooks {
    private ChatMainApplcationController chatMainApplcationController;
    private DatabaseController databaseController;

    @Inject
    public Hooks(ChatMainApplcationController chatMainApplcationController,
                 DatabaseController databaseController) {
        this.chatMainApplcationController = chatMainApplcationController;
        this.databaseController = databaseController;
    }

    @Before
    public void beforeAll() throws Exception {
        chatMainApplcationController.start();
        databaseController.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
           chatMainApplcationController.stop();
           databaseController.stop();
        }));
    }
}
