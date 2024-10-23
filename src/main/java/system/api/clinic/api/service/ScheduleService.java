package system.api.clinic.api.service;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import system.api.clinic.api.domain.Schedule;
import system.api.clinic.api.domain.enums.AvailabilityStatus;
import system.api.clinic.api.reponses.ScheduleResponse;
import system.api.clinic.api.repository.ScheduleRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final DoctorsService doctorsService;
    private final ScheduleRepository scheduleRepository;

    public String doSchedule(String name, long id) throws BadRequestException {
        List<ScheduleResponse> schedules = doctorsService.listSchedules(name);
        for (ScheduleResponse s : schedules) {
            if (s.getScheduleId() == id) {
                replaceScheduleStatus(id);
                return "Schedule done!";
            }
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
