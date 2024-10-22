package system.api.clinic.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.BadRequestException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import system.api.clinic.api.domain.User;
import system.api.clinic.api.reponses.LoginResponse;
import system.api.clinic.api.repository.UserRepository;
import system.api.clinic.api.requests.LoginRequest;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final BCryptPasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) throws BadRequestException {
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (user.isEmpty() || !passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
            throw new BadRequestException("Invalid credentials");
        }
        return tokenService.generateToken(user.get());
    }


}
