package system.api.clinic.api.exception;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

@Data
@SuperBuilder
public class ExceptionDetails {
    protected String timestamp;
    protected HttpStatus status;
    protected String details;
    private String path;
}
