package system.api.clinic.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.api.clinic.api.domain.Roles;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Roles, Long> {

    Optional<Roles> findByName(String name);

    boolean existsByName(String name);
}
