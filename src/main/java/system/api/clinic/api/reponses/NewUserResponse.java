package system.api.clinic.api.reponses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewUserResponse {

    @Schema(description = "Username", example = "Murillo")
    private String username;

    @Schema(description = "User email", example = "murillo2001@gmail.com")
    private String email;

    @Schema(description = "User password", example = "123")
    private String password;
}
