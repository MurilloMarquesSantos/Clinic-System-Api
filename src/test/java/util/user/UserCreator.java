package util.user;

import system.api.clinic.api.domain.Roles;
import system.api.clinic.api.domain.User;

import java.util.Set;

public class UserCreator {

    public static User createValidUser() {
        return User.builder()
                .id(1L)
                .username("Murillo")
                .email("murillo@gmail.com")
                .password("123")
                .roles(Set.of(new Roles(1L, "ADMIN")))
                .build();
    }
}
