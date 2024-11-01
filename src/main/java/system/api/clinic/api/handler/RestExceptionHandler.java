package system.api.clinic.api.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import system.api.clinic.api.exception.ExceptionDetails;
import system.api.clinic.api.exception.InvalidLoginException;
import system.api.clinic.api.exception.InvalidOperationException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd - HH:mm:ss");

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDetails> handleGlobalException(Exception exc, WebRequest request) {
        return new ResponseEntity<>(ExceptionDetails.builder()
                .timestamp(LocalDateTime.now().format(dateTimeFormatter))
                .status(HttpStatus.BAD_REQUEST)
                .details(exc.getMessage())
                .path(request.getDescription(false))
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidLoginException.class)
    public ResponseEntity<ExceptionDetails> handleInvalidLoginException(InvalidLoginException ile, WebRequest request) {
        return new ResponseEntity<>(ExceptionDetails.builder()
                .timestamp(LocalDateTime.now().format(dateTimeFormatter))
                .status(HttpStatus.UNAUTHORIZED)
                .details(ile.getMessage())
                .path(request.getDescription(false))
                .build(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<ExceptionDetails> handleInvalidLoginException(InvalidOperationException ioe, WebRequest request) {
        return new ResponseEntity<>(ExceptionDetails.builder()
                .timestamp(LocalDateTime.now().format(dateTimeFormatter))
                .status(HttpStatus.UNAUTHORIZED)
                .details(ioe.getMessage())
                .path(request.getDescription(false))
                .build(), HttpStatus.UNAUTHORIZED);
    }
}
