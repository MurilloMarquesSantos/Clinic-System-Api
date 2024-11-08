package system.api.clinic.api.util.doctor;

import system.api.clinic.api.domain.Doctor;
import system.api.clinic.api.domain.Schedule;
import system.api.clinic.api.reponses.FindDoctorsResponse;
import system.api.clinic.api.repository.ScheduleRepository;
import system.api.clinic.api.requests.NewDoctorRequest;

import java.util.List;

import static system.api.clinic.api.util.schedule.ScheduleCreator.createSchedule;
import static system.api.clinic.api.util.schedule.ScheduleCreator.createScheduleToBeSaved;

public class DoctorCreator {


    public static Doctor createValidDoctor() {
        return Doctor.builder()
                .id(1L)
                .name("Joao")
                .email("joao@gmail.com")
                .specialty("Cardiologist")
                .schedule(List.of(createSchedule()))
                .build();
    }


    public static Doctor createValidDoctorToBeSaved(ScheduleRepository scheduleRepository) {
        Schedule schedule = scheduleRepository.save(createScheduleToBeSaved());

        return Doctor.builder()
                .name("Joao")
                .email("joao@gmail.com")
                .specialty("Cardiologist")
                .schedule(List.of(schedule))
                .build();
    }


    public static FindDoctorsResponse createDoctorResponse() {
        return FindDoctorsResponse.builder()
                .name("Joao")
                .specialty("Cardiologist")
                .build();
    }

    public static NewDoctorRequest createDoctorRequest() {
        return NewDoctorRequest.builder()
                .name("Joao")
                .username("Joao")
                .specialty("Cardiologist")
                .email("joao@gmail.com")
                .password("123")
                .build();
    }
}
