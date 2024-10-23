package system.api.clinic.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import system.api.clinic.api.domain.Schedule;
import system.api.clinic.api.domain.User;
import system.api.clinic.api.domain.enums.AvailabilityStatus;
import system.api.clinic.api.repository.ScheduleHistoryRepository;
import system.api.clinic.api.repository.ScheduleRepository;
import system.api.clinic.api.repository.UserRepository;

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleHistoryRepository historyRepository;
    private final UserRepository userRepository;

    public String doSchedule(String name, long id, Principal principal) throws BadRequestException {
        String userId = principal.getName();
        Optional<User> user = userRepository.findById(Long.valueOf(userId));
        log.info(user.get());
        log.info("NAME: {}", principal.getName());

        Optional<Schedule> schedule = scheduleRepository.findByIdAndDoctorName(id, name);
        if (schedule.isPresent()) {
            replaceScheduleStatus(id);
            return "Schedule done!";
        }
        throw new BadRequestException("This schedule is not in this Doctor's schedule");
    }

    private void replaceScheduleStatus(long id) throws BadRequestException {
        Optional<Schedule> savedSchedule = scheduleRepository.findById(id);
        if (savedSchedule.isPresent()) {
            Schedule schedule = savedSchedule.get();
            schedule.setAvailable(AvailabilityStatus.UNAVAILABLE);
            scheduleRepository.save(savedSchedule.get());
        } else {
            throw new BadRequestException("Schedule not found with id: " + id);
        }
    }
}
