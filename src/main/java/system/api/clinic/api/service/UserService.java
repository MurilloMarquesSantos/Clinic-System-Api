package system.api.clinic.api.service;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.api.clinic.api.config.InitialConfig;
import system.api.clinic.api.domain.Doctor;
import system.api.clinic.api.domain.Schedule;
import system.api.clinic.api.domain.User;
import system.api.clinic.api.reponses.NewAdminResponse;
import system.api.clinic.api.reponses.NewDoctorResponse;
import system.api.clinic.api.reponses.NewUserResponse;
import system.api.clinic.api.repository.DoctorRepository;
import system.api.clinic.api.repository.UserRepository;
import system.api.clinic.api.requests.NewAdminRequest;
import system.api.clinic.api.requests.NewDoctorRequest;
import system.api.clinic.api.requests.NewUserRequest;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

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
                .roles(Collections.singleton(rolesService.getRoleByName("USER")))
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
                .roles(Collections.singleton(rolesService.getRoleByName("DOCTOR")))
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

    @Transactional(rollbackFor = Exception.class)
    public NewAdminResponse createNewAdmin(NewAdminRequest request) throws BadRequestException {
        User user = User.builder()
                .name(request.getName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Collections.singleton(rolesService.getRoleByName("ADMIN")))
                .build();
        userRepository.save(user);

        return NewAdminResponse.builder()
                .name(request.getName()).email(request.getEmail()).password(request.getPassword())
                .role("ADMIN").build();

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
