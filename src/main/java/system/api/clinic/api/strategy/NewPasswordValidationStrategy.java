package system.api.clinic.api.strategy;

import org.apache.coyote.BadRequestException;
import system.api.clinic.api.requests.ChangePasswordRequest;


public interface NewPasswordValidationStrategy {

    void execute(ChangePasswordRequest request) throws BadRequestException;
}
