package system.api.clinic.api.util.login;

import system.api.clinic.api.reponses.LoginResponse;
import system.api.clinic.api.requests.LoginRequest;

public class LoginCreator {

    public static LoginRequest createLoginRequest(){
        return LoginRequest.builder()
                .email("murillo@gmail.com")
                .password("123")
                .build();
    }

    public static LoginResponse createLoginResponse(){
        return LoginResponse.builder()
                .accessToken("TEST_TOKEN")
                .expiresIn(300L)
                .build();

    }


}
