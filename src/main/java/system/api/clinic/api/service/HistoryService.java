package system.api.clinic.api.service;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import system.api.clinic.api.domain.Doctor;
import system.api.clinic.api.domain.Schedule;
import system.api.clinic.api.domain.ScheduleHistory;
import system.api.clinic.api.domain.User;
import system.api.clinic.api.domain.enums.AvailabilityStatus;
import system.api.clinic.api.reponses.ScheduleHistoryResponse;
import system.api.clinic.api.reponses.ScheduleResponse;
import system.api.clinic.api.repository.ScheduleHistoryRepository;
import system.api.clinic.api.repository.UserRepository;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final ScheduleHistoryRepository historyRepository;
    private final UserRepository userRepository;

    public Page<ScheduleHistoryResponse> list(Principal principal, Pageable pageable) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd - HH:mm");

        long userId = Long.parseLong(principal.getName());

        Page<ScheduleHistory> userHistory = historyRepository.findByUserId(userId, pageable);

        List<ScheduleHistoryResponse> history = userHistory.stream()
                .map(s -> ScheduleHistoryResponse.builder()
                        .id(s.getId())
                        .doctorName(s.getDoctorName())
                        .scheduleDate(s.getScheduleDate().format(dateFormatter))
                        .build())
                .collect(Collectors.toList());

        return new PageImpl<>(history, pageable, userHistory.getTotalElements());
    }


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
