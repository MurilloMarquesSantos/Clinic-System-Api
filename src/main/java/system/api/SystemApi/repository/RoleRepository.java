package system.api.SystemApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.api.SystemApi.domain.Roles;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Roles, Long> {

    Optional<Roles> findByName(String name);
}
