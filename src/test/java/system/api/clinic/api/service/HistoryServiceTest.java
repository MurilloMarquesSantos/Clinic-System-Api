package system.api.clinic.api.service;

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
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import system.api.clinic.api.domain.Schedule;
import system.api.clinic.api.domain.ScheduleHistory;
import system.api.clinic.api.exception.InvalidOperationException;
import system.api.clinic.api.reponses.ScheduleHistoryResponse;
import system.api.clinic.api.repository.ScheduleHistoryRepository;
import system.api.clinic.api.repository.ScheduleRepository;
import system.api.clinic.api.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static system.api.clinic.api.util.schedule.ScheduleCreator.*;
import static system.api.clinic.api.util.user.UserCreator.createValidUser;

@ExtendWith(SpringExtension.class)
class HistoryServiceTest {

    @InjectMocks
    private HistoryService historyService;

    @Mock
    private ScheduleHistoryRepository historyRepositoryMock;

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private ScheduleRepository scheduleRepositoryMock;

    @Mock
    private EmailService emailServiceMock;

    @BeforeEach
    void setUp() {

        PageImpl<ScheduleHistory> historyPage = new PageImpl<>(List.of(createScheduleHistory()));

        BDDMockito.lenient().when(historyRepositoryMock.findByUserId(ArgumentMatchers.anyLong()
                        , ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(historyPage);

        BDDMockito.lenient().when(historyRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(createScheduleHistory()));

        BDDMockito.lenient().when(userRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(createValidUser()));

        BDDMockito.lenient().doNothing().when(emailServiceMock).sendAppointmentConfirmation(ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(), ArgumentMatchers.any());

        BDDMockito.lenient().doNothing().when(emailServiceMock).sendDeleteAppointmentConfirmation(
                ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.any());

        BDDMockito.lenient().when(scheduleRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(createSchedule()));
    }

    @Test
    void list_ReturnsPageOfScheduleResponse_WhenSuccessful() {

        Page<ScheduleHistoryResponse> historyPage =
                historyService.list(() -> "1", PageRequest.of(1, 1));

        assertThat(historyPage).isNotNull().isNotEmpty();

        assertThat(historyPage.toList().get(0)).isEqualTo(createScheduleHistoryResponse());
    }


    @Test
    void addHistory_AddHistoryToUser_WhenSuccessful() {

        assertThatCode(() -> historyService.addHistory(
                "Joao", () -> "1", new Schedule(), 1)).doesNotThrowAnyException();

    }

    @Test
    void addHistory_ThrowsBadRequest_WhenAnUserIdentificationErrorOccur() {
        BDDMockito.lenient().when(userRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> historyService.addHistory("Joao", () -> "1", new Schedule(), 1))
                .withMessageContaining("INVALID LOGGED USER");

    }

    @Test
    void deleteSchedule_DeleteSchedule_And_SendEmail_WhenSuccessful() {

        assertThatCode(() -> historyService.deleteSchedule(1, () -> "1"))
                .doesNotThrowAnyException();
    }

    @Test
    void deleteSchedule_ThrowsInvalidOperation_WhenScheduleUserIdIsNotEqualToUserId() {

        assertThatExceptionOfType(InvalidOperationException.class)
                .isThrownBy(() -> historyService.deleteSchedule(1, () -> "2"))
                .withMessageContaining("This schedule Id does not exists in your schedule history");
    }

    @Test
    void findScheduleById_ReturnsScheduleHistory_WhenSuccessful() throws BadRequestException {

        ScheduleHistory schedule = historyService.findScheduleById(1);

        assertThat(schedule).isNotNull().isEqualTo(createScheduleHistory());
    }

    @Test
    void findScheduleById_ThrowsBadRequest_WhenScheduleIdIsNotFound() {
        BDDMockito.lenient().when(historyRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> historyService.findScheduleById(1))
                .withMessageContaining("Invalid Schedule Id");

    }

    @Test
    void replaceScheduleStatusAvailable_ReplaceStatus_WhenSuccessful() {

        assertThatCode(() -> historyService.replaceScheduleStatusAvailable(1))
                .doesNotThrowAnyException();
    }

    @Test
    void replaceScheduleStatusAvailable_ThrowsInvalidOperation_WhenScheduleIdIsNotFound() {
        BDDMockito.lenient().when(scheduleRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        assertThatExceptionOfType(InvalidOperationException.class)
                .isThrownBy(() -> historyService.replaceScheduleStatusAvailable(1))
                .withMessageContaining("Schedule not found with id: 1");

    }

}