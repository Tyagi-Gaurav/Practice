package org.gt.chat.domain.user;

import lombok.*;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class TestUser {
    private String userId;
    private String firstName;
    private String lastName;
    private String userName;
}
