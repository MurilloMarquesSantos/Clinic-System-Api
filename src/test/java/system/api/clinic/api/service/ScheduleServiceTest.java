package system.api.clinic.api.service;

import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import system.api.clinic.api.domain.Schedule;
import system.api.clinic.api.reponses.ScheduleHistoryResponse;
import system.api.clinic.api.repository.ScheduleRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static system.api.clinic.api.util.schedule.ScheduleCreator.*;

@ExtendWith(SpringExtension.class)
class ScheduleServiceTest {

    @InjectMocks
    private ScheduleService scheduleService;

    @Mock
    private ScheduleRepository scheduleRepositoryMock;

    @Mock
    private HistoryService historyServiceMock;


    @BeforeEach
    void setUp() throws BadRequestException {
        BDDMockito.lenient().when(scheduleRepositoryMock.findByIdAndDoctorName(ArgumentMatchers.anyLong(),
                        ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(createSchedule()));

        BDDMockito.lenient().doNothing().when(historyServiceMock).addHistory(ArgumentMatchers.anyString(),
                ArgumentMatchers.any(), ArgumentMatchers.any(Schedule.class), ArgumentMatchers.anyLong());

        BDDMockito.lenient().when(scheduleRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(createSchedule()));
    }

    @Test
    void doSchedule_DoAppointment_WhenSuccessful() throws BadRequestException {

        ScheduleHistoryResponse scheduleHistoryResponse = scheduleService.doSchedule("Joao", 1, () -> "1");

        assertThat(scheduleHistoryResponse).isNotNull().isEqualTo(createScheduleHistoryResponse());
    }

    @Test
    void doSchedule_ThrowsBadRequest_WhenScheduleIdIsInvalid() {
        BDDMockito.lenient().when(scheduleRepositoryMock.findByIdAndDoctorName(ArgumentMatchers.anyLong(),
                        ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());

        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> scheduleService.doSchedule("", 1, () -> ""))
                .withMessageContaining("This schedule is not in this Doctor's schedule");

    }

    @Test
    void doSchedule_ThrowsBadRequest_WhenScheduleIsUnavailable() {
        BDDMockito.lenient().when(scheduleRepositoryMock.findByIdAndDoctorName(ArgumentMatchers.anyLong(),
                        ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(createUnavailableSchedule()));

        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> scheduleService.doSchedule("", 1, () -> ""))
                .withMessageContaining("This is schedule is unavailable");

    }

    @Test
    void replaceScheduleStatusUnavailable_ThrowsBadRequest_WhenScheduleIdIsNotFound() {
        BDDMockito.lenient().when(scheduleRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> scheduleService.replaceScheduleStatusUnavailable(1))
                .withMessageContaining("Schedule not found with id: 1");
    }

}