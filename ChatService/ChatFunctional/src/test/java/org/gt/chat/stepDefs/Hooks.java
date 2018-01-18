package org.gt.chat.stepDefs;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import cucumber.api.java.Before;

import java.util.concurrent.atomic.AtomicBoolean;

@Singleton
public class Hooks {
    private ChatMainApplicationController chatMainApplcationController;
    private ChatAuditApplicationController chatAuditApplicationController;
    private DatabaseController databaseController;
    private AtomicBoolean initialised = new AtomicBoolean(false);


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
        if (!initialised.get()) {
            databaseController.start();
            chatAuditApplicationController.start();
            chatMainApplcationController.start();
            initialised.getAndSet(true);
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            chatMainApplcationController.stop();
            chatAuditApplicationController.stop();
            databaseController.stop();
        }));
    }
}
