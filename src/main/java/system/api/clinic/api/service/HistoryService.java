package system.api.clinic.api.service;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.api.clinic.api.domain.Schedule;
import system.api.clinic.api.domain.ScheduleHistory;
import system.api.clinic.api.domain.User;
import system.api.clinic.api.domain.enums.AvailabilityStatus;
import system.api.clinic.api.exception.InvalidOperationException;
import system.api.clinic.api.reponses.ScheduleHistoryResponse;
import system.api.clinic.api.repository.ScheduleHistoryRepository;
import system.api.clinic.api.repository.ScheduleRepository;
import system.api.clinic.api.repository.UserRepository;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final ScheduleHistoryRepository historyRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final EmailService emailService;


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
                .toList();

        return new PageImpl<>(history, pageable, userHistory.getTotalElements());
    }

    private ScheduleHistory findScheduleById(long id) throws BadRequestException {
        Optional<ScheduleHistory> schedule = historyRepository.findById(id);
        if (schedule.isPresent()) {
            return schedule.get();
        } else {
            throw new BadRequestException("Invalid Schedule Id");
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public void addHistory(String doctorName, Principal principal, Schedule schedule, long scheduleId) {

        String userId = principal.getName();

        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            ScheduleHistory build = ScheduleHistory.builder()
                    .user(user)
                    .doctorName(doctorName)
                    .scheduleDate(schedule.getDateTime())
                    .scheduleId(scheduleId)
                    .build();
            historyRepository.save(build);
            emailService.sendAppointmentConfirmation(user.getEmail(), doctorName, schedule.getDateTime());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteSchedule(long id, Principal principal) throws BadRequestException {
        long userId = Long.parseLong(principal.getName());
        ScheduleHistory schedule = findScheduleById(id);
        String doctorName = schedule.getDoctorName();
        Optional<User> userOpt = userRepository.findById(userId);

        if (schedule.getUser().getId() != userId) {
            throw new InvalidOperationException("This schedule Id does not exists in your schedule history");
        }
        historyRepository.delete(schedule);
        replaceScheduleStatusAvailable(schedule.getScheduleId());
        userOpt.ifPresent(user -> emailService.sendDeleteAppointmentConfirmation(
                user.getEmail(), doctorName, schedule.getScheduleDate()));
    }

    public void replaceScheduleStatusAvailable(long id) {
        Optional<Schedule> savedSchedule = scheduleRepository.findById(id);
        if (savedSchedule.isPresent()) {
            Schedule schedule = savedSchedule.get();
            schedule.setAvailable(AvailabilityStatus.AVAILABLE);
            scheduleRepository.save(savedSchedule.get());
        } else {
            throw new InvalidOperationException("Schedule not found with id: " + id);
        }
    }
}
