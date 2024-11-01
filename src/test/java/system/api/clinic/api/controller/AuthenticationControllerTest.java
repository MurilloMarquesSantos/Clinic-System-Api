package system.api.clinic.api.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import system.api.clinic.api.reponses.LoginResponse;
import system.api.clinic.api.requests.LoginRequest;
import system.api.clinic.api.service.AuthenticationService;

import static org.assertj.core.api.Assertions.*;
import static util.LoginRequestCreator.*;
import static util.LoginResponseCreator.*;

@ExtendWith(SpringExtension.class)
class AuthenticationControllerTest {

    @InjectMocks
    private AuthenticationController authController;

    @Mock
    private AuthenticationService authServiceMock;

    @BeforeEach
    void setUp() {
        BDDMockito.when(authServiceMock.login(ArgumentMatchers.any(LoginRequest.class)))
                .thenReturn(loginResponseCreator());
    }

    @Test
    @DisplayName("login returns LoginResponse when successful")
    void login_ReturnsLoginResponseWhenSuccessful() {
        LoginResponse loginResponse = authController.login(createValidLoginRequest()).getBody();

        assertThat(loginResponse).isNotNull();

        assertThat(loginResponse.getAccessToken()).isNotEmpty().isNotNull();

        assertThat(loginResponse).isEqualTo(loginResponseCreator());
    }

}