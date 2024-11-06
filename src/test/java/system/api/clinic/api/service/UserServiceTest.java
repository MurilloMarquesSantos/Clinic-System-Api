package system.api.clinic.api.service;

import lombok.extern.log4j.Log4j2;
import org.apache.coyote.BadRequestException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import system.api.clinic.api.domain.Doctor;
import system.api.clinic.api.domain.Roles;
import system.api.clinic.api.domain.User;
import system.api.clinic.api.reponses.NewAdminResponse;
import system.api.clinic.api.reponses.NewDoctorResponse;
import system.api.clinic.api.reponses.NewUserResponse;
import system.api.clinic.api.repository.DoctorRepository;
import system.api.clinic.api.repository.UserRepository;
import system.api.clinic.api.requests.ChangePasswordRequest;
import system.api.clinic.api.requests.NewAdminRequest;
import system.api.clinic.api.requests.NewDoctorRequest;
import system.api.clinic.api.requests.NewUserRequest;
import system.api.clinic.api.strategy.NewAccountValidationStrategy;
import system.api.clinic.api.strategy.NewPasswordValidationStrategy;
import system.api.clinic.api.strategy.impl.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static system.api.clinic.api.util.doctor.DoctorCreator.createDoctorRequest;
import static system.api.clinic.api.util.doctor.DoctorCreator.createValidDoctor;
import static system.api.clinic.api.util.user.UserCreator.*;

@ExtendWith(SpringExtension.class)
@Log4j2
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private RolesService rolesServiceMock;

    @Mock
    private BCryptPasswordEncoder passwordEncoderMock;

    @Mock
    private DoctorRepository doctorRepositoryMock;

    @Mock
    private ExistingEmailValidation emailValidationMock;

    @Mock
    private ExistingUsernameValidation usernameValidationMock;

    @Mock
    private PasswordLengthValidation passwordLengthMock;

    @Mock
    private PasswordRequirementsValidation passwordRequirementsMock;

    @Mock
    private UsernameLengthValidation usernameLengthMock;

    @Mock
    private EmailService emailServiceMock;

    @Mock
    private TokenService tokenServiceMock;


    @BeforeEach
    void setUp() {
        PageImpl<User> userPage = new PageImpl<>(List.of(createValidUser()));

        List<NewAccountValidationStrategy> validationList = Arrays.asList(
                emailValidationMock,
                usernameValidationMock,
                passwordLengthMock,
                passwordRequirementsMock,
                usernameLengthMock
        );

        List<NewPasswordValidationStrategy> passwordValidationList = Arrays.asList(
                passwordLengthMock,
                passwordRequirementsMock
        );

        ReflectionTestUtils.setField(userService, "validationList", validationList);

        ReflectionTestUtils.setField(userService, "passwordValidationList", passwordValidationList);

        BDDMockito.lenient().when(userRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(userPage);

        BDDMockito.lenient().when(passwordEncoderMock.encode(ArgumentMatchers.anyString()))
                .thenReturn("123");

        BDDMockito.lenient().when(userRepositoryMock.save(ArgumentMatchers.any(User.class)))
                .thenReturn(createValidUser());

        BDDMockito.lenient().when(doctorRepositoryMock.save(ArgumentMatchers.any(Doctor.class)))
                .thenReturn(createValidDoctor());

        BDDMockito.lenient().when(userRepositoryMock.existsById(ArgumentMatchers.anyLong()))
                .thenReturn(true);

        BDDMockito.lenient().doNothing().when(userRepositoryMock).deleteById(ArgumentMatchers.anyLong());

        BDDMockito.lenient().when(userRepositoryMock.findByUsername(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(createValidUser()));

        BDDMockito.lenient().doNothing().when(emailServiceMock).sendChangePasswordRequest(ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString());

        BDDMockito.lenient().when(tokenServiceMock.generatePasswordResetToken(ArgumentMatchers.any(User.class)))
                .thenReturn("TEST_TOKEN");

        BDDMockito.lenient().when(userRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(createValidUser()));

        BDDMockito.lenient().when(tokenServiceMock.validatePasswordResetToken(ArgumentMatchers.anyString()))
                .thenReturn(true);

        BDDMockito.lenient().doNothing().when(emailServiceMock).sendChangePasswordConfirmation(
                ArgumentMatchers.anyString());
    }

    @Test
    void list_ReturnsPageOfUser_WhenSuccessful() {
        Page<User> userPage = userService.list(PageRequest.of(1, 1));

        assertThat(userPage).isNotEmpty().isNotNull();

        assertThat(userPage.toList()).hasSize(1);

        assertThat(userPage.toList().get(0)).isEqualTo(createValidUser());
    }

    @Test
    void createNewDoctor_ReturnsNewDoctorResponse_WhenSuccessful() throws BadRequestException {
        BDDMockito.lenient().doNothing().when(emailValidationMock).execute(ArgumentMatchers.any(NewDoctorRequest.class));
        BDDMockito.lenient().doNothing().when(usernameValidationMock).execute(ArgumentMatchers.any(NewDoctorRequest.class));
        BDDMockito.lenient().doNothing().when(passwordLengthMock).execute(ArgumentMatchers.any(NewDoctorRequest.class));
        BDDMockito.lenient().doNothing().when(passwordRequirementsMock).execute(ArgumentMatchers.any(NewDoctorRequest.class));
        BDDMockito.lenient().doNothing().when(usernameLengthMock).execute(ArgumentMatchers.any(NewDoctorRequest.class));

        BDDMockito.lenient().when(rolesServiceMock.getRoleByName(ArgumentMatchers.anyString()))
                .thenReturn(new Roles(3L, "DOCTOR"));

        NewDoctorResponse newDoctor = userService.createNewDoctor(createDoctorRequest());

        Assertions.assertThat(newDoctor).isNotNull().isEqualTo(createNewDoctorResponse());
    }

    @Test
    void createNewAdmin_ReturnsNewAdminResponse_WhenSuccessful() throws BadRequestException {
        BDDMockito.lenient().doNothing().when(emailValidationMock).execute(ArgumentMatchers.any(NewAdminRequest.class));
        BDDMockito.lenient().doNothing().when(usernameValidationMock).execute(ArgumentMatchers.any(NewAdminRequest.class));
        BDDMockito.lenient().doNothing().when(passwordLengthMock).execute(ArgumentMatchers.any(NewAdminRequest.class));
        BDDMockito.lenient().doNothing().when(passwordRequirementsMock).execute(ArgumentMatchers.any(NewAdminRequest.class));
        BDDMockito.lenient().doNothing().when(usernameLengthMock).execute(ArgumentMatchers.any(NewAdminRequest.class));

        BDDMockito.lenient().when(rolesServiceMock.getRoleByName(ArgumentMatchers.anyString()))
                .thenReturn(new Roles(1L, "ADMIN"));

        NewAdminResponse newAdmin = userService.createNewAdmin(createAdminRequest());

        Assertions.assertThat(newAdmin).isNotNull().isEqualTo(createNewAdminResponse());
    }

    @Test
    void createNewUser_ReturnsNewUserResponse_WhenSuccessful() throws BadRequestException {
        BDDMockito.lenient().doNothing().when(emailValidationMock).execute(ArgumentMatchers.any(NewUserRequest.class));
        BDDMockito.lenient().doNothing().when(usernameValidationMock).execute(ArgumentMatchers.any(NewUserRequest.class));
        BDDMockito.lenient().doNothing().when(passwordLengthMock).execute(ArgumentMatchers.any(NewUserRequest.class));
        BDDMockito.lenient().doNothing().when(passwordRequirementsMock).execute(ArgumentMatchers.any(NewUserRequest.class));
        BDDMockito.lenient().doNothing().when(usernameLengthMock).execute(ArgumentMatchers.any(NewUserRequest.class));

        BDDMockito.lenient().when(rolesServiceMock.getRoleByName(ArgumentMatchers.anyString()))
                .thenReturn(new Roles(2L, "USER"));

        NewUserResponse newUser = userService.createNewUser(createUserRequest());

        Assertions.assertThat(newUser).isNotNull().isEqualTo(createNewUserResponse());
    }

    @Test
    void deleteById_ThrowsBadRequest_WhenIdDoesNotExists() {
        BDDMockito.lenient().when(userRepositoryMock.existsById(ArgumentMatchers.anyLong()))
                .thenReturn(false);

        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> userService.deleteById(0))
                .withMessageContaining("No user found with this ID");

    }

    @Test
    void deleteById_RemovesUser_WhenSuccessful() {
        assertThatCode(() -> userService.deleteById(0))
                .doesNotThrowAnyException();
    }

    @Test
    void loadByUsername_ReturnsUserDetails_WhenSuccessful() {

        UserDetails userReturned = userService.loadUserByUsername("");

        assertThat(userReturned).isNotNull().isEqualTo(createValidUser());
    }

    @Test
    void loadByUsername_ThrowsUsernameNotFoundException_WhenUsernameIsNotFound() {
        BDDMockito.when(userRepositoryMock.findByUsername(ArgumentMatchers.anyString()))
                .thenThrow(new UsernameNotFoundException("User not found"));

        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> userService.loadUserByUsername(""))
                .withMessageContaining("User not found");
    }

    @Test
    void requestPasswordReset_SendEmail_WhenSuccessful() {

        assertThatCode(() -> userService.requestPasswordReset(() -> "1"))
                .doesNotThrowAnyException();
    }

    @Test
    void updatePassword_ChangeUserPassword_And_SendEmail_WhenSuccessful() throws BadRequestException {
        BDDMockito.lenient().doNothing().when(passwordLengthMock).execute(ArgumentMatchers.any(
                ChangePasswordRequest.class));
        BDDMockito.lenient().doNothing().when(passwordRequirementsMock).execute(ArgumentMatchers.any(
                ChangePasswordRequest.class));

        assertThatCode(() -> userService.updatePassword(() -> "1", new ChangePasswordRequest(), ""))
                .doesNotThrowAnyException();
    }

    @Test
    void updatePassword_ThrowsBadRequestException_WhenTokenIsInvalid() {
        BDDMockito.lenient().when(tokenServiceMock.validatePasswordResetToken(ArgumentMatchers.anyString()))
                .thenReturn(false);

        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> userService.updatePassword(() -> "1", new ChangePasswordRequest(), ""))
                .withMessageContaining("INVALID TOKEN, EXPIRED");

    }
}