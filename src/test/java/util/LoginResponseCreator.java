package util;

import system.api.clinic.api.reponses.LoginResponse;

public class LoginResponseCreator {

    public static LoginResponse loginResponseCreator(){
        return LoginResponse.builder()
                .accessToken("TEST_TOKEN")
                .expiresIn(300L)
                .build();

    }
}
