package system.api.SystemApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.api.SystemApi.domain.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
