package system.api.clinic.api.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NewAdminRequest {

    private String name;

    private String username;

    private String email;

    private String password;
}
