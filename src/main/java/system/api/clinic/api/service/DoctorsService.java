package system.api.clinic.api.service;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import system.api.clinic.api.domain.Doctor;
import system.api.clinic.api.domain.enums.AvailabilityStatus;
import system.api.clinic.api.reponses.FindDoctorsResponse;
import system.api.clinic.api.reponses.ScheduleResponse;
import system.api.clinic.api.repository.DoctorRepository;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorsService {

    private final DoctorRepository doctorRepository;

    public Page<FindDoctorsResponse> listAll(Pageable pageable) {
        List<Doctor> doctors = doctorRepository.findAll(pageable).toList();
        List<FindDoctorsResponse> docs = new ArrayList<>();
        for (Doctor doc : doctors) {
            FindDoctorsResponse build = FindDoctorsResponse.builder()
                    .name(doc.getName())
                    .specialty(doc.getSpecialty())
                    .build();
            docs.add(build);
        }
        return new PageImpl<>(docs, pageable, doctorRepository.count());
    }

    public Page<ScheduleResponse> listSchedulesPage(String name, Pageable pageable) throws BadRequestException {
        List<Doctor> doctorsList = findDoctors(name);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd - HH:mm");

        List<ScheduleResponse> allSchedules = new ArrayList<>();

        for (Doctor doctor : doctorsList) {
            List<ScheduleResponse> list = doctor.getSchedule().stream()
                    .filter(s -> s.getAvailable().equals(AvailabilityStatus.AVAILABLE))
                    .map(schedules -> ScheduleResponse.builder()
                            .scheduleId(schedules.getId())
                            .doctorName(doctor.getName())
                            .specialty(doctor.getSpecialty())
                            .dateTime(schedules.getDateTime().format(dateFormatter))
                            .build())
                    .toList();

            allSchedules.addAll(list);
        }

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), allSchedules.size());
        List<ScheduleResponse> paginatedList = allSchedules.subList(start, end);

        return new PageImpl<>(paginatedList, pageable, allSchedules.size());
    }

    public Page<FindDoctorsResponse> listBySpecialty(String specialty, Pageable pageable) {
        List<Doctor> doctors = doctorRepository.findBySpecialty(specialty);
        List<FindDoctorsResponse> docs = new ArrayList<>();
        for (Doctor doc : doctors) {
            FindDoctorsResponse build = FindDoctorsResponse.builder()
                    .name(doc.getName())
                    .specialty(doc.getSpecialty())
                    .build();
            docs.add(build);
        }
        return new PageImpl<>(docs, pageable, doctors.size());
    }

    public List<Doctor> findDoctors(String name) throws BadRequestException {
        List<Doctor> doctorList = doctorRepository.findByName(name);
        if (doctorList.isEmpty()) {
            throw new BadRequestException("No doctor found with this name");
        }
        return doctorList;
    }

}
