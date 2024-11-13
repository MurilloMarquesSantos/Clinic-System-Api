package system.api.clinic.api.util.password;

import system.api.clinic.api.requests.ChangePasswordRequest;

public class PasswordCreator {

    public static ChangePasswordRequest createPasswordRequest(){
        return ChangePasswordRequest.builder()
                .password("Murillo@123")
                .build();
    }
}
