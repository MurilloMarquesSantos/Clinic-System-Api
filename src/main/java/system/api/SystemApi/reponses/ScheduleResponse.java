package system.api.SystemApi.reponses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleResponse {

    private Long scheduleId;
    private String doctorName;
    private String specialty;
    private String dateTime;
}
