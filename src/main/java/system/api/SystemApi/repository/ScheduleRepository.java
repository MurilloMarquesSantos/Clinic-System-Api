package system.api.SystemApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.api.SystemApi.domain.Schedules;

public interface ScheduleRepository extends JpaRepository<Schedules, Long> {
}
