package system.api.clinic.api.reponses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NewDoctorResponse {

    @Schema(description = "Doctor name", example = "Murillo")
    private String name;

    @Schema(description = "Doctor specialty", example = "Cardiologist")
    private String specialty;

    @Schema(description = "Doctor email", example = "murillo@gmail.com")
    private String email;

    @Schema(description = "Doctor password", example = "123")
    private String password;
}
