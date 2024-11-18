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
public class NewAdminRequest {

    @Schema(description = "New name", example = "Murillo")
    private String name;

    @Schema(description = "New username", example = "Murillo")
    private String username;

    @Schema(description = "New email", example = "murillo@gmail.com")
    private String email;

    @Schema(description = "Nem password", example = "123")
    private String password;
}
