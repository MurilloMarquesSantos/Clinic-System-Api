package system.api.clinic.api.strategy.impl;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;
import system.api.clinic.api.requests.NewAdminRequest;
import system.api.clinic.api.requests.NewDoctorRequest;
import system.api.clinic.api.requests.NewUserRequest;
import system.api.clinic.api.strategy.NewAccountValidationStrategy;

@Component
public class PasswordLengthValidation implements NewAccountValidationStrategy {

    private static final String MESSAGE = "The password must have at least 8 and maximum of 12 characters";

    @Override
    public void execute(NewDoctorRequest request) throws BadRequestException {
        if (request.getPassword().length() < 8 || request.getPassword().length() > 12) {
            throw new BadRequestException(MESSAGE);
        }
    }

    @Override
    public void execute(NewAdminRequest request) throws BadRequestException {
        if (request.getPassword().length() < 8 || request.getPassword().length() > 12) {
            throw new BadRequestException(MESSAGE);
        }
    }

    @Override
    public void execute(NewUserRequest request) throws BadRequestException {
        if (request.getPassword().length() < 8 || request.getPassword().length() > 12) {
            throw new BadRequestException(MESSAGE);
        }
    }
}
