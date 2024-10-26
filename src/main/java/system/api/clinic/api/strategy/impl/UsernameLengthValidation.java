package system.api.clinic.api.strategy.impl;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;
import system.api.clinic.api.requests.NewAdminRequest;
import system.api.clinic.api.requests.NewDoctorRequest;
import system.api.clinic.api.requests.NewUserRequest;
import system.api.clinic.api.strategy.NewAccountValidationStrategy;

@Component
public class UsernameLengthValidation implements NewAccountValidationStrategy {

    public static final String MESSAGE = "The username must have more than 2 characters";

    @Override
    public void execute(NewDoctorRequest request) throws BadRequestException {
        if (request.getUsername().length() <= 2) {
            throw new BadRequestException(MESSAGE);
        }
    }

    @Override
    public void execute(NewAdminRequest request) throws BadRequestException {
        if (request.getUsername().length() <= 2) {
            throw new BadRequestException(MESSAGE);
        }
    }

    @Override
    public void execute(NewUserRequest request) throws BadRequestException {
        if (request.getUsername().length() <= 2) {
            throw new BadRequestException(MESSAGE);
        }
    }
}
