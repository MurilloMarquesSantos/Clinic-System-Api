package system.api.clinic.api.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewUserRequest {

    @Schema(description = "User name", example = "Murillo")
    private String name;

    @Schema(description = "Username", example = "Murillo")
    private String username;

    @Schema(description = "User email", example = "murillo@gmail.com")
    private String email;

    @Schema(description = "User password", example = "123")
    private String password;
}
