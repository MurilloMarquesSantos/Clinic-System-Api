package system.api.clinic.api.strategy.impl;

import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import system.api.clinic.api.requests.ChangePasswordRequest;
import system.api.clinic.api.requests.NewAdminRequest;
import system.api.clinic.api.requests.NewDoctorRequest;
import system.api.clinic.api.requests.NewUserRequest;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static system.api.clinic.api.util.doctor.DoctorCreator.createDoctorRequest;
import static system.api.clinic.api.util.password.PasswordCreator.createPasswordRequest;
import static system.api.clinic.api.util.user.UserCreator.createAdminRequest;
import static system.api.clinic.api.util.user.UserCreator.createUserRequest;

@ExtendWith(SpringExtension.class)
class PasswordRequirementsValidationTest {

    @InjectMocks
    private PasswordRequirementsValidation passwordValidation;

    private static final String MESSAGE = "The password must have at least, 1 lower case char, 1 upper case char" +
            ",1 number, 1 special char";

    @Test
    void execute_ChecksPasswordRequirements() {

        NewAdminRequest adminRequest = createAdminRequest();
        adminRequest.setPassword("Murillo@123");

        NewUserRequest userRequest = createUserRequest();
        userRequest.setPassword("Murillo@123");

        NewDoctorRequest doctorRequest = createDoctorRequest();
        doctorRequest.setPassword("Murillo@123");

        assertThatCode(() -> passwordValidation.execute(adminRequest))
                .doesNotThrowAnyException();

        assertThatCode(() -> passwordValidation.execute(userRequest))
                .doesNotThrowAnyException();

        assertThatCode(() -> passwordValidation.execute(doctorRequest))
                .doesNotThrowAnyException();

        assertThatCode(() -> passwordValidation.execute(createPasswordRequest()))
                .doesNotThrowAnyException();
    }

    @Test
    void execute_ThrowsBadRequest_WhenRequirementsDoNotMatch() {

        ChangePasswordRequest passwordRequest = createPasswordRequest();
        passwordRequest.setPassword("123");

        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> passwordValidation.execute(createAdminRequest()))
                .withMessageContaining(MESSAGE);

        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> passwordValidation.execute(createUserRequest()))
                .withMessageContaining(MESSAGE);

        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> passwordValidation.execute(createDoctorRequest()))
                .withMessageContaining(MESSAGE);

        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> passwordValidation.execute(passwordRequest))
                .withMessageContaining(MESSAGE);
    }

}