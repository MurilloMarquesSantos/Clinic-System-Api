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
public class ScheduleResponse {

    @Schema(description = "Schedule Id", example = "1")
    private Long scheduleId;

    @Schema(description = "Doctor name", example = "Murillo")
    private String doctorName;

    @Schema(description = "Doctor Specialty", example = "Cardiologist")
    private String specialty;

    @Schema(description = "Schedule date", example = "20/01 - 10:30")
    private String dateTime;
}
