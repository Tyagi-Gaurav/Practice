package org.gt.chat.stepDefs.service;

import com.google.inject.Inject;
import cucumber.runtime.java.guice.ScenarioScoped;
import org.gt.chat.domain.user.TestUser;
import org.gt.chat.stepDefs.exception.TestInvalidDataStateException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@ScenarioScoped
public class MockUserService {
    private Map<String, TestUser> userMap = new HashMap();
    private MockDatabaseService mockDatabaseService;

    @Inject
    public MockUserService(MockDatabaseService mockDatabaseService) {
        this.mockDatabaseService = mockDatabaseService;
    }

    public void createDefaultTestUserFor(String userName) {
        TestUser testUser = TestUser.builder()
                .userId(UUID.randomUUID().toString())
                .build();
        userMap.put(userName, testUser);
        mockDatabaseService.createUser(testUser);
    }

    public String getUserIdFor(String userName) {
        Optional<TestUser> testUser = Optional.ofNullable(userMap.get(userName));
        return testUser.orElseThrow(() -> new TestInvalidDataStateException("Have you created the user " + userName)).getUserId();
    }
}
