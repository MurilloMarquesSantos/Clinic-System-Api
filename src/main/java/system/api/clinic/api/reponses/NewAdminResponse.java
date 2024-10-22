package system.api.clinic.api.reponses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewAdminResponse {

    private String name;

    private String email;

    private String password;

    private String role;

}
