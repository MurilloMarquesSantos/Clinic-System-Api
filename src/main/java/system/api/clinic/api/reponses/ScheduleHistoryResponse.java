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
public class ScheduleHistoryResponse {

    @Schema(description = "Schedule History Id", example = "1")
    private long id;

    @Schema(description = "Doctor name from schedule", example = "Murillo")
    private String doctorName;

    @Schema(description = "Schedule date", example = "20/01 - 10:30")
    private String scheduleDate;
}
