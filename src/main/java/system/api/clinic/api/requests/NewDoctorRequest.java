package system.api.clinic.api.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewDoctorRequest {

    private String username;

    private String name;

    private String specialty;

    private String email;

    private String password;

}
