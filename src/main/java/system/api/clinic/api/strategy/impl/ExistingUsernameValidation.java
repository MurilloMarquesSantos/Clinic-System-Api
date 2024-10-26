package system.api.clinic.api.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;
import system.api.clinic.api.repository.UserRepository;
import system.api.clinic.api.requests.NewAdminRequest;
import system.api.clinic.api.requests.NewDoctorRequest;
import system.api.clinic.api.requests.NewUserRequest;
import system.api.clinic.api.strategy.NewAccountValidationStrategy;

@Component
@RequiredArgsConstructor
public class ExistingUsernameValidation implements NewAccountValidationStrategy {

    private final UserRepository userRepository;
    private static final String MESSAGE = "USERNAME UNAVAILABLE";

    @Override
    public void execute(NewDoctorRequest request) throws BadRequestException {
        if (isInvalidUsername(request.getUsername())) {
            throw new BadRequestException(MESSAGE);
        }

    }

    @Override
    public void execute(NewAdminRequest request) throws BadRequestException {
        if (isInvalidUsername(request.getUsername())) {
            throw new BadRequestException(MESSAGE);
        }
    }

    @Override
    public void execute(NewUserRequest request) throws BadRequestException {
        if (isInvalidUsername(request.getUsername())) {
            throw new BadRequestException(MESSAGE);
        }
    }

    private boolean isInvalidUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
