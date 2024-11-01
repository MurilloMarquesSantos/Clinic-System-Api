package util;

import system.api.clinic.api.requests.LoginRequest;

public class LoginRequestCreator {

    public static LoginRequest createValidLoginRequest(){
        return LoginRequest.builder()
                .email("murillo@gmail.com")
                .password("123")
                .build();
    }
}
