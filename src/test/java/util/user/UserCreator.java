package util.user;

import system.api.clinic.api.domain.Roles;
import system.api.clinic.api.domain.User;
import system.api.clinic.api.reponses.NewAdminResponse;
import system.api.clinic.api.reponses.NewDoctorResponse;
import system.api.clinic.api.reponses.NewUserResponse;

import java.util.Set;

public class UserCreator {

    public static User createValidUser() {
        return User.builder()
                .id(1L)
                .name("Murillo")
                .username("Murillo")
                .email("murillo@gmail.com")
                .password("123")
                .roles(Set.of(new Roles(1L, "ADMIN")))
                .build();
    }

    public static NewUserResponse createNewUserResponse() {
        return NewUserResponse.builder()
                .email("murillo@gmail.com")
                .username("Murillo")
                .password("123")
                .build();
    }

    public static NewAdminResponse createNewAdminResponse() {
        return NewAdminResponse.builder()
                .name("Murillo")
                .email("murillo@gmail.com")
                .password("123")
                .role("ADMIN")
                .build();
    }

    public static NewDoctorResponse createNewDoctorResponse() {
        return NewDoctorResponse.builder()
                .name("Joao")
                .specialty("Cardiologist")
                .email("joao@gmail.com")
                .password("123")
                .build();
    }

}