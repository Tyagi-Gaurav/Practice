package org.gt.chat.stepDefs;

import com.google.inject.Inject;
import cucumber.api.java.Before;
import cucumber.runtime.java.guice.ScenarioScoped;

@ScenarioScoped
public class Hooks {
    private ChatMainApplicationController chatMainApplcationController;
    private ChatAuditApplicationController chatAuditApplicationController;
    private DatabaseController databaseController;

    @Inject
    public Hooks(ChatMainApplicationController chatMainApplcationController,
                 ChatAuditApplicationController chatAuditApplicationController,
                 DatabaseController databaseController) {
        this.chatMainApplcationController = chatMainApplcationController;
        this.chatAuditApplicationController = chatAuditApplicationController;
        this.databaseController = databaseController;
    }

    @Before
    public void beforeAll() throws Exception {
        databaseController.start();
        chatAuditApplicationController.start();
        chatMainApplcationController.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            chatMainApplcationController.stop();
            chatAuditApplicationController.stop();
            databaseController.stop();
        }));
    }
}
