package system.api.clinic.api.reponses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewAdminResponse {

    @Schema(description = "Admin name", example = "Murillo")
    private String name;

    @Schema(description = "Admin email", example = "murillo@gmail.com")
    private String email;

    @Schema(description = "Admin password", example = "123")
    private String password;

    @Schema(description = "Admin role", example = "ADMIN")
    private String role;

}
