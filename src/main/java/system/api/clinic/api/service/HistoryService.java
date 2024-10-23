package system.api.clinic.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import system.api.clinic.api.domain.Schedule;
import system.api.clinic.api.domain.ScheduleHistory;
import system.api.clinic.api.domain.User;
import system.api.clinic.api.repository.ScheduleHistoryRepository;
import system.api.clinic.api.repository.UserRepository;

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final ScheduleHistoryRepository historyRepository;
    private final UserRepository userRepository;

    public void addHistory(String doctorName, Principal principal, Schedule schedule) {
        String name = principal.getName();

        Optional<User> userOpt = userRepository.findById(Long.valueOf(name));

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            ScheduleHistory build = ScheduleHistory.builder()
                    .user(user)
                    .doctorName(doctorName)
                    .scheduleDate(schedule.getDateTime())
                    .build();
            historyRepository.save(build);
        }
    }
}
