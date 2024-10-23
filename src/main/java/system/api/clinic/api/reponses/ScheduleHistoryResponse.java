package system.api.clinic.api.reponses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleHistoryResponse {

    private long id;
    private String doctorName;
    private String scheduleDate;
}
