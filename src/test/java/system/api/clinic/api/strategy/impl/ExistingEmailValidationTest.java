package system.api.clinic.api.strategy.impl;

import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import system.api.clinic.api.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static system.api.clinic.api.util.doctor.DoctorCreator.createDoctorRequest;
import static system.api.clinic.api.util.user.UserCreator.createAdminRequest;
import static system.api.clinic.api.util.user.UserCreator.createUserRequest;

@ExtendWith(SpringExtension.class)
class ExistingEmailValidationTest {

    @InjectMocks
    private ExistingEmailValidation emailValidation;

    @Mock
    private UserRepository userRepositoryMock;

    private static final String MESSAGE = "There is already an user with this email!";

    @BeforeEach
    void setUp() {
        BDDMockito.when(userRepositoryMock.existsByEmail(ArgumentMatchers.anyString()))
                .thenReturn(false);
    }

    @Test
    void execute_ChecksIfEmailAlreadyExists() {
        assertThatCode(() -> emailValidation.execute(createDoctorRequest()))
                .doesNotThrowAnyException();
        assertThatCode(() -> emailValidation.execute(createUserRequest()))
                .doesNotThrowAnyException();
        assertThatCode(() -> emailValidation.execute(createAdminRequest()))
                .doesNotThrowAnyException();
    }

    @Test
    void execute_ThrowsBadRequest_WhenEmailExists() {
        BDDMockito.when(userRepositoryMock.existsByEmail(ArgumentMatchers.anyString()))
                .thenReturn(true);

        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> emailValidation.execute(createDoctorRequest()))
                .withMessageContaining(MESSAGE);

        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> emailValidation.execute(createUserRequest()))
                .withMessageContaining(MESSAGE);

        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> emailValidation.execute(createAdminRequest()))
                .withMessageContaining(MESSAGE);
    }

}