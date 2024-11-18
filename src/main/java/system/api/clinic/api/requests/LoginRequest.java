package system.api.clinic.api.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LoginRequest {

    @Schema(description = "Already existing email in database", example = "murillo@gmail.com")
    private String email;

    @Schema(description = "Already existing password for previous email", example = "123")
    private String password;
}
