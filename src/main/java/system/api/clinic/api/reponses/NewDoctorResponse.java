package system.api.clinic.api.reponses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NewDoctorResponse {

    private String name;

    private String specialty;

    private String email;

    private String password;
}
