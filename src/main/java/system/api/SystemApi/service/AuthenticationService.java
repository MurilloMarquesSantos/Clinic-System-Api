package system.api.SystemApi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import system.api.SystemApi.domain.User;
import system.api.SystemApi.repository.UserRepository;
import system.api.SystemApi.requests.LoginRequest;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenService tokenService;

    private boolean isLoginValid(LoginRequest request) {
        Optional<User> userEmail = userRepository.findByEmail(request.getEmail());
        return userEmail.isPresent() && request.getPassword().equals(userEmail.get().getPassword());
    }

    public String login(LoginRequest request) {
        if (isLoginValid(request)) {
            Optional<User> user = userRepository.findByEmail(request.getEmail());
            return tokenService.generateToken(user.get());
        }
        return "Invalid credentials";
    }


}
