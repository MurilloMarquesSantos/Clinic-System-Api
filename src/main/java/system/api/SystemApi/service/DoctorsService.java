package system.api.SystemApi.service;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import system.api.SystemApi.domain.Doctors;
import system.api.SystemApi.domain.enums.AvailabilityStatus;
import system.api.SystemApi.reponses.ScheduleResponse;
import system.api.SystemApi.repository.DoctorRepository;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorsService {

    private final DoctorRepository doctorRepository;

    public List<ScheduleResponse> listSchedules(String name) throws BadRequestException {
        Doctors doctor = doctorRepository.findByName(name).orElseThrow
                (() -> new BadRequestException("Doctor not found"));

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd - HH:mm");

        return doctor.getSchedules().stream()
                .filter(s -> s.getAvailable().equals(AvailabilityStatus.AVAILABLE))
                .map(schedules -> ScheduleResponse.builder()
                        .doctorName(name)
                        .dateTime(schedules.getDateTime().format(dateFormatter))
                        .build())
                .toList();
    }
}
