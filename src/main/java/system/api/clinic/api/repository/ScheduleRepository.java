package system.api.clinic.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.api.clinic.api.domain.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
