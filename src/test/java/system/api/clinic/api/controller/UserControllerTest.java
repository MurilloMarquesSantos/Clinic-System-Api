package system.api.clinic.api.controller;

import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import system.api.clinic.api.domain.User;
import system.api.clinic.api.reponses.NewAdminResponse;
import system.api.clinic.api.reponses.NewDoctorResponse;
import system.api.clinic.api.reponses.NewUserResponse;
import system.api.clinic.api.reponses.ScheduleHistoryResponse;
import system.api.clinic.api.requests.ChangePasswordRequest;
import system.api.clinic.api.requests.NewAdminRequest;
import system.api.clinic.api.requests.NewDoctorRequest;
import system.api.clinic.api.requests.NewUserRequest;
import system.api.clinic.api.service.HistoryService;
import system.api.clinic.api.service.UserService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static util.schedule.ScheduleCreator.createScheduleHistoryResponse;
import static util.user.UserCreator.*;

@ExtendWith(SpringExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userServiceMock;

    @Mock
    private HistoryService historyServiceMock;

    @BeforeEach
    void setUp() throws BadRequestException {

        PageImpl<User> userPage = new PageImpl<>(List.of(createValidUser()));

        PageImpl<ScheduleHistoryResponse> historyPage = new PageImpl<>(List.of(createScheduleHistoryResponse()));

        BDDMockito.lenient().when(userServiceMock.list(ArgumentMatchers.any()))
                .thenReturn(userPage);

        BDDMockito.lenient().when(userServiceMock.createNewUser(ArgumentMatchers.any(NewUserRequest.class)))
                .thenReturn(createNewUserResponse());

        BDDMockito.lenient().when(userServiceMock.createNewAdmin(ArgumentMatchers.any(NewAdminRequest.class)))
                .thenReturn(createNewAdminResponse());

        BDDMockito.lenient().when(userServiceMock.createNewDoctor(ArgumentMatchers.any(NewDoctorRequest.class)))
                .thenReturn(createNewDoctorResponse());

        BDDMockito.lenient().when(historyServiceMock.list(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(historyPage);

        BDDMockito.lenient().doNothing().when(historyServiceMock)
                .deleteSchedule(ArgumentMatchers.anyLong(), ArgumentMatchers.any());

        BDDMockito.lenient().doNothing().when(userServiceMock).deleteById(ArgumentMatchers.anyLong());

        BDDMockito.lenient().doNothing().when(userServiceMock).requestPasswordReset(ArgumentMatchers.any());

        BDDMockito.lenient().doNothing().when(userServiceMock)
                .updatePassword(ArgumentMatchers.any(),
                        ArgumentMatchers.any(ChangePasswordRequest.class), ArgumentMatchers.anyString());
    }

    @Test
    void listAll_ReturnsPageOfUser_WhenSuccessful() {

        Page<User> userPage = userController.listAll(null).getBody();

        assertThat(userPage).isNotEmpty().isNotNull();

        assertThat(userPage.toList().get(0)).isEqualTo(createValidUser());

        assertThat(userPage.toList()).hasSize(1);
    }

    @Test
    void register_ReturnsNewUserResponse_WhenSuccessful() throws BadRequestException {

        NewUserResponse user = userController.register(new NewUserRequest()).getBody();

        assertThat(user).isNotNull().isEqualTo(createNewUserResponse());
    }

    @Test
    void registerAdmin_ReturnsNewAdminResponse_WhenSuccessful() throws BadRequestException {

        NewAdminResponse admin = userController.registerAdmin(new NewAdminRequest()).getBody();

        assertThat(admin).isNotNull().isEqualTo(createNewAdminResponse());
    }

    @Test
    void registerDoc_ReturnsNewDoctorResponse_WhenSuccessful() throws BadRequestException {

        NewDoctorResponse doctor = userController.registerDoc(new NewDoctorRequest()).getBody();

        assertThat(doctor).isNotNull().isEqualTo(createNewDoctorResponse());
    }

    @Test
    void userHistory_ReturnsPageOfHistoryResponse_WhenSuccessful() {

        Page<ScheduleHistoryResponse> userHistory = userController.userHistory(null, null).getBody();

        assertThat(userHistory).isNotNull().isNotEmpty();

        assertThat(userHistory.toList().get(0)).isEqualTo(createScheduleHistoryResponse());

        assertThat(userHistory.toList()).hasSize(1);
    }

    @Test
    void deleteSchedule_RemovesScheduleFromUserHistory_WhenSuccessful() {

        assertThatCode(() -> userController.deleteSchedule(0, null))
                .doesNotThrowAnyException();
    }

    @Test
    void deleteUser_RemovesUser_WhenSuccessful() {

        assertThatCode(() -> userController.deleteUser(0))
                .doesNotThrowAnyException();
    }

    @Test
    void resetPasswordRequest_SendEmailResetLink_WhenSuccessful() {

        assertThatCode(() -> userController.resetPasswordRequest(null))
                .doesNotThrowAnyException();
    }

    @Test
    void resetPassword_UpdatesUserPassword_WhenSuccessful() {

        assertThatCode(() -> userController.resetPassword("", new ChangePasswordRequest(), null))
                .doesNotThrowAnyException();
    }
}