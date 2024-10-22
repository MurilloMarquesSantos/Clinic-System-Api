package system.api.clinic.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.api.clinic.api.domain.Doctor;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    List<Doctor> findByName(String name);

    boolean existsByNameAndSpecialtyAndEmail(String name, String specialty, String email);
}
