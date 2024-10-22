package system.api.clinic.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import system.api.clinic.api.domain.Roles;
import system.api.clinic.api.domain.User;
import system.api.clinic.api.reponses.LoginResponse;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class TokenService {

    private final JwtEncoder jwtEncoder;

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
}