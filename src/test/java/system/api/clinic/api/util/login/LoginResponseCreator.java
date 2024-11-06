package system.api.clinic.api.util.login;

import system.api.clinic.api.reponses.LoginResponse;

public class LoginResponseCreator {

    public static LoginResponse createLoginResponse(){
        return LoginResponse.builder()
                .accessToken("TEST_TOKEN")
                .expiresIn(300L)
                .build();

    }
}
