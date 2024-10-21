package system.api.SystemApi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import system.api.SystemApi.domain.Doctor;
import system.api.SystemApi.domain.Schedule;
import system.api.SystemApi.domain.enums.AvailabilityStatus;
import system.api.SystemApi.repository.DoctorRepository;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitialConfig implements CommandLineRunner {

    private final DoctorRepository doctorRepository;

    private static List<Schedule> createSchedule(){
        return List.of(Schedule.builder()
                        .dateTime(LocalDateTime.of(2024, 11, 22, 10, 0))
                        .available(AvailabilityStatus.AVAILABLE)
                        .build(),
                Schedule.builder()
                        .dateTime(LocalDateTime.of(2024, 11, 22, 11, 0))
                        .available(AvailabilityStatus.AVAILABLE)
                        .build(),
                Schedule.builder()
                        .dateTime(LocalDateTime.of(2024, 11, 23, 13, 0))
                        .available(AvailabilityStatus.AVAILABLE)
                        .build(),
                Schedule.builder()
                        .dateTime(LocalDateTime.of(2024, 11, 22, 11, 30))
                        .available(AvailabilityStatus.AVAILABLE)
                        .build(),
                Schedule.builder()
                        .dateTime(LocalDateTime.of(2024, 11, 22, 14, 0))
                        .available(AvailabilityStatus.AVAILABLE)
                        .build(),
                Schedule.builder()
                        .dateTime(LocalDateTime.of(2024, 11, 23, 10, 0))
                        .available(AvailabilityStatus.AVAILABLE)
                        .build(),
                Schedule.builder()
                        .dateTime(LocalDateTime.of(2024, 11, 24, 15, 30))
                        .available(AvailabilityStatus.AVAILABLE)
                        .build(),
                Schedule.builder()
                        .dateTime(LocalDateTime.of(2024, 11, 24, 14, 0))
                        .available(AvailabilityStatus.AVAILABLE)
                        .build(),
                Schedule.builder()
                        .dateTime(LocalDateTime.of(2024, 11, 22, 9, 30))
                        .available(AvailabilityStatus.AVAILABLE)
                        .build());
    }
    @Override
    public void run(String... args) throws Exception {


        List<Doctor> doctorsList = List.of(
                Doctor.builder()
                        .name("Joao")
                        .specialty("Cardiologist")
                        .schedule(createSchedule())
                        .build(),
                Doctor.builder()
                        .name("Maria")
                        .specialty("Dermatologist")
                        .schedule(createSchedule())
                        .build(),
                Doctor.builder()
                        .name("Pedro")
                        .specialty("Orthopedist")
                        .schedule(createSchedule())
                        .build(),
                Doctor.builder()
                        .name("Julia")
                        .specialty("Gynecologist")
                        .schedule(createSchedule())
                        .build(),
                Doctor.builder()
                        .name("Fabio")
                        .specialty("Ophthalmologist")
                        .schedule(createSchedule())
                        .build()
        );

        for (Doctor doc : doctorsList) {
            doc.getSchedule().forEach(schedule -> schedule.setDoctor(doc));
            boolean exists = doctorRepository.existsByNameAndSpecialty(doc.getName(), doc.getSpecialty());
            if (!exists) {
                doctorRepository.save(doc);
            }
        }
    }


}
