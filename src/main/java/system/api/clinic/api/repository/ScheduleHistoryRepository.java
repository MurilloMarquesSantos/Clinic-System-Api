package system.api.clinic.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.api.clinic.api.domain.ScheduleHistory;

public interface ScheduleHistoryRepository extends JpaRepository<ScheduleHistory, Long> {
}
