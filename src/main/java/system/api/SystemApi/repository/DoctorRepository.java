package system.api.SystemApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.api.SystemApi.domain.Doctors;

import java.util.Optional;

public interface DoctorRepository  extends JpaRepository<Doctors, Long> {

    Optional<Doctors> findByName(String name);
}
