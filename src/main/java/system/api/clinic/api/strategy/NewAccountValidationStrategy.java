package system.api.clinic.api.strategy;

import org.apache.coyote.BadRequestException;
import system.api.clinic.api.requests.NewAdminRequest;
import system.api.clinic.api.requests.NewDoctorRequest;
import system.api.clinic.api.requests.NewUserRequest;


public interface NewAccountValidationStrategy {
    void execute(NewDoctorRequest request) throws BadRequestException;

    void execute(NewAdminRequest request) throws BadRequestException;

    void execute(NewUserRequest request) throws  BadRequestException;
}
