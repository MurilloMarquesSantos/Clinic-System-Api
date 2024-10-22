package system.api.clinic.api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import system.api.clinic.api.domain.Doctor;
import system.api.clinic.api.domain.Schedule;
import system.api.clinic.api.domain.enums.AvailabilityStatus;
import system.api.clinic.api.repository.DoctorRepository;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitialConfig implements CommandLineRunner {

    private final DoctorRepository doctorRepository;

    public static List<Schedule> createSchedule() {
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
                        .email("joao@gmail.com")
                        .schedule(createSchedule())
                        .build(),
                Doctor.builder()
                        .name("Maria")
                        .specialty("Dermatologist")
                        .email("maria@gmail.com")
                        .schedule(createSchedule())
                        .build(),
                Doctor.builder()
                        .name("Pedro")
                        .specialty("Orthopedist")
                        .email("pedro@gmail.com")
                        .schedule(createSchedule())
                        .build(),
                Doctor.builder()
                        .name("Julia")
                        .specialty("Gynecologist")
                        .email("julia@gmail.com")
                        .schedule(createSchedule())
                        .build(),
                Doctor.builder()
                        .name("Fabio")
                        .specialty("Ophthalmologist")
                        .email("fabio@gmail.com")
                        .schedule(createSchedule())
                        .build()
        );

        for (Doctor doc : doctorsList) {
            doc.getSchedule().forEach(schedule -> schedule.setDoctor(doc));
            boolean exists = doctorRepository.existsByNameAndSpecialtyAndEmail(
                    doc.getName(), doc.getSpecialty(), doc.getEmail());
            if (!exists) {
                doctorRepository.save(doc);
            }
        }
    }


}
