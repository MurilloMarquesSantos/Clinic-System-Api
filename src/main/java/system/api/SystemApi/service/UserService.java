package system.api.SystemApi.service;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.api.SystemApi.domain.User;
import system.api.SystemApi.reponses.NewUserResponse;
import system.api.SystemApi.repository.UserRepository;
import system.api.SystemApi.requests.NewUserRequest;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RolesService rolesService;

    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = Exception.class)
    public NewUserResponse createNewUser(NewUserRequest request) throws BadRequestException {
        User user = User.builder()
                .name(request.getName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Collections.singleton(rolesService.getRoleByName("ROLE_USER")))
                .build();
        userRepository.save(user);

        return NewUserResponse.builder()
                .username(request.getUsername()).email(request.getEmail()).password(request.getPassword()).build();

    }
}
