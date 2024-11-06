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
import system.api.clinic.api.reponses.FindDoctorsResponse;
import system.api.clinic.api.reponses.ScheduleHistoryResponse;
import system.api.clinic.api.reponses.ScheduleResponse;
import system.api.clinic.api.service.DoctorsService;
import system.api.clinic.api.service.ScheduleService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static system.api.clinic.api.util.doctor.DoctorCreator.createDoctorResponse;
import static system.api.clinic.api.util.schedule.ScheduleCreator.createScheduleHistoryResponse;
import static system.api.clinic.api.util.schedule.ScheduleCreator.createScheduleResponse;

@ExtendWith(SpringExtension.class)
class DoctorControllerTest {

    @InjectMocks
    private DoctorController doctorController;

    @Mock
    private DoctorsService doctorsServiceMock;

    @Mock
    private ScheduleService scheduleServiceMock;

    @BeforeEach
    void setUp() throws BadRequestException {
        PageImpl<FindDoctorsResponse> doctorsPage = new PageImpl<>(List.of(createDoctorResponse()));
        PageImpl<ScheduleResponse> schedulePage = new PageImpl<>(List.of(createScheduleResponse()));

        BDDMockito.lenient().when(doctorsServiceMock.listAll(ArgumentMatchers.any()))
                .thenReturn(doctorsPage);

        BDDMockito.lenient().when(doctorsServiceMock.listSchedulesPage(ArgumentMatchers.anyString(), ArgumentMatchers.any()))
                .thenReturn(schedulePage);

        BDDMockito.lenient().when(doctorsServiceMock.listBySpecialty(ArgumentMatchers.anyString(), ArgumentMatchers.any()))
                .thenReturn(doctorsPage);

        BDDMockito.lenient().when(scheduleServiceMock.doSchedule(ArgumentMatchers.anyString(), ArgumentMatchers.anyLong(),
                        ArgumentMatchers.any()))
                .thenReturn(createScheduleHistoryResponse());
    }


    @Test
    void listAll_ReturnsPageOfDoctorsResponse_WhenSuccessful() {

        Page<FindDoctorsResponse> doctorsPage = doctorController.listAll(null).getBody();

        assertThat(doctorsPage).isNotNull().isNotEmpty();

        assertThat(doctorsPage.toList()).hasSize(1);

        assertThat(doctorsPage.toList().get(0)).isEqualTo(createDoctorResponse());

    }

    @Test
    void schedule_ReturnsPageOfScheduleResponse_WhenSuccessful() throws BadRequestException {

        Page<ScheduleResponse> scheduleResponsePage = doctorController.schedule("", null).getBody();

        assertThat(scheduleResponsePage).isNotEmpty().isNotNull();

        assertThat(scheduleResponsePage.toList()).isNotEmpty().hasSize(1);

        assertThat(scheduleResponsePage.toList().get(0)).isEqualTo(createScheduleResponse());
    }


    @Test
    void listBySpecialty_ReturnsPageOfDoctorsResponse_WhenSuccessful() {

        Page<FindDoctorsResponse> doctorsPage = doctorController.listBySpecialty("", null).getBody();

        assertThat(doctorsPage).isNotNull().isNotEmpty();

        assertThat(doctorsPage.toList()).hasSize(1);

        assertThat(doctorsPage.toList().get(0)).isEqualTo(createDoctorResponse());
    }

    @Test
    void doSchedule_ReturnsScheduleHistoryResponse_WhenSuccessful() throws BadRequestException {

        ScheduleHistoryResponse schedule = doctorController.doSchedule("", 0, null).getBody();

        assertThat(schedule).isNotNull().isEqualTo(createScheduleHistoryResponse());

    }

}