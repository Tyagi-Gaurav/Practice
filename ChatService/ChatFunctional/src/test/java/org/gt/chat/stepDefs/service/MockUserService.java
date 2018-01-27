package org.gt.chat.stepDefs.service;

import cucumber.runtime.java.guice.ScenarioScoped;
import org.gt.chat.domain.user.TestUser;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@ScenarioScoped
public class MockUserService {
    private Map<String, TestUser> userMap = new HashMap();
    private MockDatabaseService mockDatabaseService;

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
}
