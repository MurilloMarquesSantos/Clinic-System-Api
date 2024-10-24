package system.api.clinic.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidLoginException extends RuntimeException {
    public InvalidLoginException() {
        super("INVALID LOGIN CREDENTIALS");
    }

}
