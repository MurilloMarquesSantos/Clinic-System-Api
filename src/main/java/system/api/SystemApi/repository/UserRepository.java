package system.api.SystemApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.api.SystemApi.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
