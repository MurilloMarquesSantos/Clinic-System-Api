package system.api.clinic.api.util.schedule;

import system.api.clinic.api.domain.Schedule;
import system.api.clinic.api.domain.ScheduleHistory;
import system.api.clinic.api.domain.enums.AvailabilityStatus;
import system.api.clinic.api.reponses.ScheduleHistoryResponse;
import system.api.clinic.api.reponses.ScheduleResponse;

import java.time.LocalDateTime;

import static system.api.clinic.api.util.user.UserCreator.createValidUser;

public class ScheduleCreator {

    public static Schedule createSchedule() {
        return Schedule.builder()
                .id(1L)
                .dateTime(LocalDateTime.of(2024, 11, 22, 10, 0))
                .available(AvailabilityStatus.AVAILABLE)
                .build();
    }

    public static ScheduleResponse createScheduleResponse() {
        return ScheduleResponse.builder()
                .scheduleId(1L)
                .doctorName("Joao")
                .specialty("Cardiologist")
                .dateTime("11/22 - 10:00")
                .build();
    }

    public static ScheduleHistoryResponse createScheduleHistoryResponse() {
        return ScheduleHistoryResponse.builder()
                .id(1L)
                .doctorName("Joao")
                .scheduleDate("11/22 - 10:00")
                .build();
    }

    public static ScheduleHistory createScheduleHistory() {
        return ScheduleHistory.builder()
                .id(1L)
                .user(createValidUser())
                .doctorName("Joao")
                .scheduleId(1L)
                .scheduleDate(LocalDateTime.of(2024, 11, 22, 10, 0))
                .build();
    }

}
