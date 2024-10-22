package system.api.SystemApi.service;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import system.api.SystemApi.domain.Doctor;
import system.api.SystemApi.domain.enums.AvailabilityStatus;
import system.api.SystemApi.reponses.ScheduleResponse;
import system.api.SystemApi.repository.DoctorRepository;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorsService {

    private final DoctorRepository doctorRepository;

    public Page<ScheduleResponse> listSchedules(String name, Pageable pageable) throws BadRequestException {
        Doctor doctor = doctorRepository.findByName(name).orElseThrow
                (() -> new BadRequestException("Doctor not found"));

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd - HH:mm");

        List<ScheduleResponse> list = doctor.getSchedule().stream()
                .filter(s -> s.getAvailable().equals(AvailabilityStatus.AVAILABLE))
                .map(schedules -> ScheduleResponse.builder()
                        .scheduleId(schedules.getId())
                        .doctorName(name)
                        .dateTime(schedules.getDateTime().format(dateFormatter))
                        .build())
                .toList();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        List<ScheduleResponse> paginatedList = list.subList(start, end);

        return new PageImpl<>(paginatedList, pageable, list.size());
    }
}
