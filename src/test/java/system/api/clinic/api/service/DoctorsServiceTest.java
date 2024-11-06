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
import system.api.clinic.api.domain.Doctor;
import system.api.clinic.api.reponses.FindDoctorsResponse;
import system.api.clinic.api.reponses.ScheduleResponse;
import system.api.clinic.api.repository.DoctorRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static util.doctor.DoctorCreator.createDoctorResponse;
import static util.doctor.DoctorCreator.createValidDoctor;
import static util.schedule.ScheduleCreator.createScheduleResponse;

@ExtendWith(SpringExtension.class)
class DoctorsServiceTest {

    @InjectMocks
    private DoctorsService doctorsService;

    @Mock
    private DoctorRepository doctorRepositoryMock;

    @BeforeEach
    void setUp() {
        PageImpl<Doctor> doctorPage = new PageImpl<>(List.of(createValidDoctor()));

        BDDMockito.lenient().when(doctorRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(doctorPage);

        BDDMockito.lenient().when(doctorRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(createValidDoctor()));

        BDDMockito.lenient().when(doctorRepositoryMock.findBySpecialty(ArgumentMatchers.anyString()))
                .thenReturn(List.of(createValidDoctor()));
    }

    @Test
    void listAll_ReturnsPageOfDoctorResponse_WhenSuccessful() {

        Page<FindDoctorsResponse> findDoctorsResponses = doctorsService.listAll(
                PageRequest.of(1, 1));

        assertThat(findDoctorsResponses).isNotNull().isNotEmpty();

        assertThat(findDoctorsResponses.toList().get(0)).isEqualTo(createDoctorResponse());
    }

    @Test
    void listSchedulesPage_ReturnsPageOfScheduleResponse_WhenSuccessful() throws BadRequestException {

        Page<ScheduleResponse> scheduleResponses = doctorsService.listSchedulesPage(
                "", PageRequest.of(0, 10));

        assertThat(scheduleResponses).isNotEmpty().isNotEmpty();

        assertThat(scheduleResponses.toList().get(0)).isEqualTo(createScheduleResponse());
    }

    @Test
    void listBySpecialty_ReturnsPageOfDoctorResponse_WhenSuccessful() {

        Page<FindDoctorsResponse> doctorPage = doctorsService.listBySpecialty("", PageRequest.of(0, 10));

        assertThat(doctorPage).isNotNull().isNotEmpty();

        assertThat(doctorPage.toList().get(0)).isEqualTo(createDoctorResponse());

    }

    @Test
    void findDoctors_ThrowsBadRequestException_WhenDoctorIsNotFound() {
        BDDMockito.when(doctorRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of());

        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> doctorsService.findDoctors(""))
                .withMessageContaining("No doctor found with this name");
    }


}