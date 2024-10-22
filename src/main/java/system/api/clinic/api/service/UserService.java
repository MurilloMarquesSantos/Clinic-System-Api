package system.api.clinic.api.service;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.api.clinic.api.config.InitialConfig;
import system.api.clinic.api.domain.Doctor;
import system.api.clinic.api.domain.Schedule;
import system.api.clinic.api.domain.User;
import system.api.clinic.api.reponses.NewDoctorResponse;
import system.api.clinic.api.reponses.NewUserResponse;
import system.api.clinic.api.repository.DoctorRepository;
import system.api.clinic.api.repository.UserRepository;
import system.api.clinic.api.requests.NewDoctorRequest;
import system.api.clinic.api.requests.NewUserRequest;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RolesService rolesService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final DoctorRepository doctorRepository;

    @Transactional(rollbackFor = Exception.class)
    public NewUserResponse createNewUser(NewUserRequest request) throws BadRequestException {
        User user = User.builder()
                .name(request.getName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Collections.singleton(rolesService.getRoleByName("ROLE_USER")))
                .build();
        userRepository.save(user);

        return NewUserResponse.builder()
                .username(request.getUsername()).email(request.getEmail()).password(request.getPassword()).build();

    }

    @Transactional(rollbackFor = Exception.class)
    public NewDoctorResponse createNewDoctor(NewDoctorRequest request) throws BadRequestException {
        User user = User.builder()
                .name(request.getName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Collections.singleton(rolesService.getRoleByName("ROLE_DOCTOR")))
                .build();
        userRepository.save(user);

        Doctor doctor = Doctor.builder()
                .name(request.getName())
                .specialty(request.getSpecialty())
                .email(request.getEmail())
                .build();

        List<Schedule> schedule = InitialConfig.createSchedule();
        schedule.forEach(s -> s.setDoctor(doctor));

        doctor.setSchedule(schedule);
        doctorRepository.save(doctor);

        return NewDoctorResponse.builder()
                .name(doctor.getName())
                .specialty(doctor.getSpecialty())
                .email(doctor.getEmail())
                .password(request.getPassword())
                .build();
    }

}
