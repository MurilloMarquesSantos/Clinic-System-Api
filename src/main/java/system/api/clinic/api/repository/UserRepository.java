package system.api.clinic.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.api.clinic.api.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
