package system.api.clinic.api.strategy.impl;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;
import system.api.clinic.api.requests.ChangePasswordRequest;
import system.api.clinic.api.requests.NewAdminRequest;
import system.api.clinic.api.requests.NewDoctorRequest;
import system.api.clinic.api.requests.NewUserRequest;
import system.api.clinic.api.strategy.NewAccountValidationStrategy;
import system.api.clinic.api.strategy.NewPasswordValidationStrategy;

@Component
public class PasswordRequirementsValidation implements NewAccountValidationStrategy, NewPasswordValidationStrategy {

    private static final String MESSAGE = "The password must have at least, 1 lower case char, 1 upper case char" +
            ",1 number, 1 special char";

    private static final String REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).+$";

    @Override
    public void execute(NewDoctorRequest request) throws BadRequestException {
        if (!request.getPassword().matches(REGEX)) {
            throw new BadRequestException(MESSAGE);
        }
    }

    @Override
    public void execute(NewAdminRequest request) throws BadRequestException {
        if (!request.getPassword().matches(REGEX)) {
            throw new BadRequestException(MESSAGE);
        }

    }

    @Override
    public void execute(NewUserRequest request) throws BadRequestException {
        if (!request.getPassword().matches(REGEX)) {
            throw new BadRequestException(MESSAGE);
        }
    }

    @Override
    public void execute(ChangePasswordRequest request) throws BadRequestException {
        if (!request.getPassword().matches(REGEX)) {
            throw new BadRequestException(MESSAGE);
        }
    }
}
