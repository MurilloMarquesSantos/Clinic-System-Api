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
public class FindDoctorsResponse {

    @Schema(description = "Doctor name", example = "Murillo")
    private String name;

    @Schema(description = "Doctor Specialty", example = "Cardiologist")
    private String specialty;
}
