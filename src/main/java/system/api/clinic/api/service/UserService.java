package system.api.clinic.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import system.api.clinic.api.mapper.DoctorMapper;
import system.api.clinic.api.mapper.ResponseMapper;
import system.api.clinic.api.mapper.UserMapper;
import system.api.clinic.api.reponses.NewAdminResponse;
import system.api.clinic.api.reponses.NewDoctorResponse;
import system.api.clinic.api.reponses.NewUserResponse;
import system.api.clinic.api.repository.DoctorRepository;
import system.api.clinic.api.repository.UserRepository;
import system.api.clinic.api.requests.NewAdminRequest;
import system.api.clinic.api.requests.NewDoctorRequest;
import system.api.clinic.api.requests.NewUserRequest;
import system.api.clinic.api.strategy.NewAccountValidationStrategy;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RolesService rolesService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final DoctorRepository doctorRepository;
    private final List<NewAccountValidationStrategy> validationList;

    public Page<User> list(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    public NewDoctorResponse createNewDoctor(NewDoctorRequest request) throws BadRequestException {
        for (NewAccountValidationStrategy validation : validationList) {
            validation.execute(request);
        }

        User user = UserMapper.INSTANCE.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Collections.singleton(rolesService.getRoleByName("DOCTOR")));

        userRepository.save(user);


        Doctor doctor = DoctorMapper.INSTANCE.toDoctor(request);

        List<Schedule> schedule = InitialConfig.createSchedule();
        schedule.forEach(s -> s.setDoctor(doctor));

        doctor.setSchedule(schedule);
        doctorRepository.save(doctor);

        return ResponseMapper.INSTANCE.toDocResponse(request);
    }

    @Transactional(rollbackFor = Exception.class)
    public NewUserResponse createNewUser(NewUserRequest request) throws BadRequestException {
        for (NewAccountValidationStrategy validation : validationList) {
            validation.execute(request);
        }

        User user = UserMapper.INSTANCE.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Collections.singleton(rolesService.getRoleByName("USER")));

        userRepository.save(user);

        return ResponseMapper.INSTANCE.toUserResponse(request);
    }

    @Transactional(rollbackFor = Exception.class)
    public NewAdminResponse createNewAdmin(NewAdminRequest request) throws BadRequestException {
        for (NewAccountValidationStrategy validation : validationList) {
            validation.execute(request);
        }

        User user = UserMapper.INSTANCE.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Collections.singleton(rolesService.getRoleByName("ADMIN")));
        userRepository.save(user);

        NewAdminResponse admResponse = ResponseMapper.INSTANCE.toAdmResponse(request);
        admResponse.setRole("ADMIN");
        return admResponse;

    }

    public void deleteById(long id) throws BadRequestException {
        if (!userRepository.existsById(id)) {
            throw new BadRequestException("No user found with this ID");
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
