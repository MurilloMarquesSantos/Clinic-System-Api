package util.doctor;

import system.api.clinic.api.domain.Doctor;
import system.api.clinic.api.reponses.FindDoctorsResponse;

import java.time.LocalDateTime;
import java.util.List;

import static util.schedule.ScheduleCreator.*;

public class DoctorCreator {

    public static final LocalDateTime DATE_TIME = LocalDateTime.MAX;

    public static Doctor createValidDoctor() {
        return Doctor.builder()
                .id(1L)
                .name("Joao")
                .email("joao@gmail.com")
                .specialty("Cardiologist")
                .schedule(List.of(createSchedule()))
                .build();
    }

    public static FindDoctorsResponse createDoctorResponse() {
        return FindDoctorsResponse.builder()
                .name("Joao")
                .specialty("Cardiologist")
                .build();
    }
}
