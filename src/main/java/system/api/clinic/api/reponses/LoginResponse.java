package system.api.clinic.api.reponses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {


    @Schema(description = "Access token", example = "123vdgadaj23h4h")
    private String accessToken;

    @Schema(description = "Time for token to expire", example = "300")
    private Long expiresIn;
}
