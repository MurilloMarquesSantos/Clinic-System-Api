package system.api.clinic.api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import system.api.clinic.api.domain.Doctor;
import system.api.clinic.api.domain.Schedule;
import system.api.clinic.api.domain.User;
import system.api.clinic.api.domain.enums.AvailabilityStatus;
import system.api.clinic.api.repository.DoctorRepository;
import system.api.clinic.api.repository.RoleRepository;
import system.api.clinic.api.repository.UserRepository;
import system.api.clinic.api.service.RolesService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitialConfig implements CommandLineRunner {

    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RolesService rolesService;

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

    public static List<Doctor> createDoctors() {
        return List.of(
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
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        List<User> users = List.of(
                User.builder()
                        .name("Murillo")
                        .username("Murillo")
                        .email("murillo@gmail.com")
                        .password(passwordEncoder.encode("123"))
                        .roles(Collections.singleton(rolesService.getRoleByName("ADMIN")))
                        .build(),
                User.builder()
                        .name("Joao")
                        .username("Joao")
                        .email("joao@gmail.com")
                        .password(passwordEncoder.encode("123"))
                        .roles(Collections.singleton(rolesService.getRoleByName("DOCTOR")))
                        .build()
        );

        List<Doctor> doctorsList = createDoctors();
        for (Doctor doc : doctorsList) {
            doc.getSchedule().forEach(schedule -> schedule.setDoctor(doc));
            boolean exists = doctorRepository.existsByNameAndSpecialtyAndEmail(
                    doc.getName(), doc.getSpecialty(), doc.getEmail());
            if (!exists) {
                doctorRepository.save(doc);
            }
        }

        for (User user : users) {
            boolean exists = userRepository.existsByEmail(user.getEmail());
            if (!exists) {
                userRepository.save(user);
            }
        }
    }


}
