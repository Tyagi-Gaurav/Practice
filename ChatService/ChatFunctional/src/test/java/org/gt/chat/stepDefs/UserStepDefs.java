package org.gt.chat.stepDefs;

import com.google.inject.Inject;
import cucumber.api.java.en.And;
import cucumber.runtime.java.guice.ScenarioScoped;
import org.gt.chat.stepDefs.service.MockUserService;

@ScenarioScoped
public class UserStepDefs {
    private MockUserService mockUserService;

    @Inject
    public UserStepDefs(MockUserService mockUserService) {
        this.mockUserService = mockUserService;
    }

    @And("^a user (.*) already exists on the system$")
    public void aUserAliceAlreadyExistsOnTheSystem(String userName) throws Throwable {
        mockUserService.createDefaultTestUserFor(userName);
    }
}
