package system.api.clinic.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import system.api.clinic.api.domain.PasswordResetToken;
import system.api.clinic.api.domain.Roles;
import system.api.clinic.api.domain.User;
import system.api.clinic.api.reponses.LoginResponse;
import system.api.clinic.api.repository.ResetTokenRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtEncoder jwtEncoder;
    private final ResetTokenRepository resetTokenRepository;

    public LoginResponse generateToken(User user) {

        Instant now = Instant.now();
        long expiresIn = 300L;

        String scope = user.getRoles()
                .stream()
                .map(Roles::getName)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("backend")
                .subject(String.valueOf(user.getId()))
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scope)
                .build();

        String token = jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();

        return LoginResponse.builder().accessToken(token).expiresIn(expiresIn).build();

    }

    public String generatePasswordResetToken(User user) {

        String token = UUID.randomUUID().toString();

        PasswordResetToken tokenToSave = PasswordResetToken.builder()
                .userId(user.getId())
                .token(token)
                .expirationDate(LocalDateTime.now().plusMinutes(5))
                .build();
        resetTokenRepository.save(tokenToSave);

        return token;
    }

    public boolean validatePasswordResetToken(String token) {
        Optional<PasswordResetToken> tokenOpt = resetTokenRepository.findByToken(token);
        return tokenOpt.isPresent() && tokenOpt.get().getExpirationDate().isAfter(LocalDateTime.now());
    }
}
