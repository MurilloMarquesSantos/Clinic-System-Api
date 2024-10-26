package system.api.clinic.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import system.api.clinic.api.domain.Schedule;
import system.api.clinic.api.domain.enums.AvailabilityStatus;
import system.api.clinic.api.reponses.ScheduleHistoryResponse;
import system.api.clinic.api.repository.ScheduleRepository;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final HistoryService historyService;

    public ScheduleHistoryResponse doSchedule(String name, long id, Principal principal) throws BadRequestException {

        Optional<Schedule> schedule = scheduleRepository.findByIdAndDoctorName(id, name);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd - HH:mm");

        if (schedule.isPresent()) {
            if (schedule.get().getAvailable() == AvailabilityStatus.UNAVAILABLE) {
                throw new BadRequestException("This is schedule is unavailable");
            }
            replaceScheduleStatusUnavailable(id);
            historyService.addHistory(name, principal, schedule.get(), id);

            return ScheduleHistoryResponse.builder()
                    .doctorName(name)
                    .id(id)
                    .scheduleDate(schedule.get().getDateTime().format(dateFormatter))
                    .build();
        }
        throw new BadRequestException("This schedule is not in this Doctor's schedule");
    }

    public void replaceScheduleStatusUnavailable(long id) throws BadRequestException {
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
