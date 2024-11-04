package system.api.clinic.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import system.api.clinic.api.domain.User;
import system.api.clinic.api.exception.InvalidLoginException;
import system.api.clinic.api.reponses.LoginResponse;
import system.api.clinic.api.repository.UserRepository;
import system.api.clinic.api.requests.LoginRequest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static util.login.LoginRequestCreator.*;
import static util.login.LoginResponseCreator.*;
import static util.user.UserCreator.createValidUser;

@ExtendWith(SpringExtension.class)
class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService authService;

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private TokenService tokenServiceMock;

    @Mock
    private BCryptPasswordEncoder passwordEncoderMock;

    @BeforeEach
    void setUp() {
        BDDMockito.when(userRepositoryMock.findByEmail(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(createValidUser()));
        BDDMockito.lenient().when(passwordEncoderMock.matches(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                .thenReturn(true);
        BDDMockito.lenient().when(tokenServiceMock.generateToken(ArgumentMatchers.any(User.class)))
                .thenReturn(createLoginResponse());
    }

    @Test
    void login_ReturnsLoginResponse_WhenSuccessful() {

        LoginResponse loginResponse = authService.login(createLoginRequest());

        assertThat(loginResponse).isNotNull().isEqualTo(createLoginResponse());

        assertThat(loginResponse.getAccessToken()).isEqualTo("TEST_TOKEN");
    }

    @Test
    void login_ThrowsInvalidLoginException_WhenUserNotFound() {
        LoginRequest loginRequest = createLoginRequest();

        BDDMockito.when(userRepositoryMock.findByEmail(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());

        assertThatExceptionOfType(InvalidLoginException.class)
                .isThrownBy(() -> authService.login(loginRequest))
                .withMessageContaining("INVALID LOGIN CREDENTIALS");
    }

    @Test
    void login_ThrowsInvalidLoginException_WhenPasswordDoesNotMatch() {
        LoginRequest loginRequest = createLoginRequest();

        BDDMockito.when(passwordEncoderMock.matches(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                .thenReturn(false);

        assertThatExceptionOfType(InvalidLoginException.class)
                .isThrownBy(() -> authService.login(loginRequest))
                .withMessageContaining("INVALID LOGIN CREDENTIALS");
    }


}