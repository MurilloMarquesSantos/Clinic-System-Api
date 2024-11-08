package system.api.clinic.api.util.token;

import system.api.clinic.api.domain.PasswordResetToken;

import java.time.LocalDateTime;

public class ResetTokenCreator {

    public static PasswordResetToken createResetToken() {
        return PasswordResetToken.builder()
                .id(1L)
                .token("TEST_TOKEN")
                .userId(1L)
                .expirationDate(LocalDateTime.of(2024, 11, 22, 10, 0))
                .build();
    }

    public static PasswordResetToken createResetTokenToBeSaved() {
        return PasswordResetToken.builder()
                .token("TEST_TOKEN")
                .userId(1L)
                .expirationDate(LocalDateTime.of(2024, 11, 22, 10, 0))
                .build();
    }
}
