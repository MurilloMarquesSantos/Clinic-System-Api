package system.api.clinic.api.strategy.impl;

import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import system.api.clinic.api.requests.NewAdminRequest;
import system.api.clinic.api.requests.NewDoctorRequest;
import system.api.clinic.api.requests.NewUserRequest;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static system.api.clinic.api.util.doctor.DoctorCreator.createDoctorRequest;
import static system.api.clinic.api.util.user.UserCreator.createAdminRequest;
import static system.api.clinic.api.util.user.UserCreator.createUserRequest;

@ExtendWith(SpringExtension.class)
class UsernameLengthValidationTest {

    @InjectMocks
    private UsernameLengthValidation usernameValidation;

    public static final String MESSAGE = "The username must have more than 2 characters";
    
    @Test
    void execute_ChecksUsernameLength() {

        assertThatCode(() -> usernameValidation.execute(createDoctorRequest()))
                .doesNotThrowAnyException();

        assertThatCode(() -> usernameValidation.execute(createUserRequest()))
                .doesNotThrowAnyException();

        assertThatCode(() -> usernameValidation.execute(createAdminRequest()))
                .doesNotThrowAnyException();
    }
    
    @Test
    void execute_ThrowsBadRequestException_WhenUsernameLengthIsLowerThan3(){
        
        NewAdminRequest adminRequest = createAdminRequest();
        adminRequest.setUsername("ts");

        NewUserRequest userRequest = createUserRequest();
        userRequest.setUsername("ts");

        NewDoctorRequest doctorRequest = createDoctorRequest();
        doctorRequest.setUsername("ts");
        
        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> usernameValidation.execute(adminRequest))
                .withMessageContaining(MESSAGE);

        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> usernameValidation.execute(userRequest))
                .withMessageContaining(MESSAGE);

        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> usernameValidation.execute(doctorRequest))
                .withMessageContaining(MESSAGE);

    }
    
    

}