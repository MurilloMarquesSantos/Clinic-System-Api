package system.api.clinic.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import system.api.clinic.api.reponses.LoginResponse;
import system.api.clinic.api.repository.ResetTokenRepository;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static system.api.clinic.api.util.login.LoginCreator.createLoginResponse;
import static system.api.clinic.api.util.token.ResetTokenCreator.createResetToken;
import static system.api.clinic.api.util.user.UserCreator.createValidUser;

@ExtendWith(SpringExtension.class)
class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    @Mock
    private JwtEncoder jwtEncoderMock;

    @Mock
    private ResetTokenRepository resetRepositoryMock;


    @BeforeEach
    void setUp() {
        Jwt jwtToken = new Jwt(
                "TEST_TOKEN",
                Instant.now(),
                Instant.now().plusSeconds(300L),
                Map.of("alg", "none"),
                Map.of("scope", "user")
        );

        BDDMockito.lenient().when(jwtEncoderMock.encode(ArgumentMatchers.any(JwtEncoderParameters.class)))
                .thenReturn(jwtToken);

        BDDMockito.lenient().when(resetRepositoryMock.findByToken(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(createResetToken()));
    }

    @Test
    void generateToken_ReturnsLoginResponse_WhenSuccessful() {

        LoginResponse loginResponse = tokenService.generateToken(createValidUser());

        assertThat(loginResponse).isNotNull();

        assertThat(loginResponse.getAccessToken()).isEqualTo("TEST_TOKEN");

        assertThat(loginResponse).isEqualTo(createLoginResponse());
    }

    @Test
    void generatePasswordResetToken_ReturnsToken_WhenSuccessful() {

        String token = tokenService.generatePasswordResetToken(createValidUser());

        assertThat(token).isNotNull().isNotEmpty().hasSize(36);

    }

    @Test
    void validatePasswordResetToken_ReturnsTrue_WhenTokenIsValid() {

        boolean valid = tokenService.validatePasswordResetToken("");

        assertThat(valid).isTrue();
    }

    @Test
    void validatePasswordResetToken_ReturnsFalse_WhenTokenIsInvalid() {

        BDDMockito.lenient().when(resetRepositoryMock.findByToken(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());

        boolean valid = tokenService.validatePasswordResetToken("");

        assertThat(valid).isFalse();
    }
}