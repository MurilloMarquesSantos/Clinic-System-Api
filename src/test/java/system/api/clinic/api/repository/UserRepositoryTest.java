package system.api.clinic.api.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import system.api.clinic.api.domain.User;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static system.api.clinic.api.util.user.UserCreator.createValidUser;
import static system.api.clinic.api.util.user.UserCreator.createValidUserToBeSaved;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void existsByEmail_ReturnsTrue_WhenSuccessful() {

        User savedUser = userRepository.save(createValidUserToBeSaved(roleRepository));

        boolean exists = userRepository.existsByEmail(savedUser.getEmail());

        assertThat(exists).isTrue();
    }

    @Test
    void save_PersistsUser_WhenSuccessful() {
        User savedUser = userRepository.save(createValidUserToBeSaved(roleRepository));

        assertThat(savedUser).isNotNull().isEqualTo(createValidUser());

    }

    @Test
    void findByEmail_ReturnsOptUser_WhenSuccessful() {
        User savedUser = userRepository.save(createValidUserToBeSaved(roleRepository));

        Optional<User> user = userRepository.findByEmail(savedUser.getEmail());

        assertThat(user).isPresent().isNotNull().isNotEmpty().contains(savedUser);

    }

    @Test
    void findById_ReturnsOptOfUser_WhenSuccessful() {

        User savedUser = userRepository.save(createValidUserToBeSaved(roleRepository));

        Optional<User> user = userRepository.findById(savedUser.getId());

        assertThat(user).isPresent().isNotNull().isNotEmpty().contains(savedUser);
    }

    @Test
    void findAll_ReturnsListOfUser_WhenSuccessful() {

        User savedUser = userRepository.save(createValidUserToBeSaved(roleRepository));

        List<User> users = userRepository.findAll();

        assertThat(users).isNotNull().isNotEmpty().hasSize(1).contains(savedUser);

    }

    @Test
    void delete_RemovesUser_WhenSuccessful() {
        User savedUser = userRepository.save(createValidUserToBeSaved(roleRepository));

        userRepository.delete(savedUser);

        Optional<User> userOpt = userRepository.findById(savedUser.getId());

        assertThat(userOpt).isEmpty();

    }

    @Test
    void existsById_ReturnsTrue_WhenSuccessful() {
        User savedUser = userRepository.save(createValidUserToBeSaved(roleRepository));

        boolean exists = userRepository.existsById(savedUser.getId());

        assertThat(exists).isTrue();

    }

    @Test
    void deleteById_RemovesUserById_WhenSuccessful() {
        User savedUser = userRepository.save(createValidUserToBeSaved(roleRepository));

        userRepository.deleteById(savedUser.getId());

        Optional<User> userOpt = userRepository.findById(savedUser.getId());

        assertThat(userOpt).isEmpty();

    }

    @Test
    void findByUsername_ReturnsOptOfUser_WhenSuccessful() {

        User savedUser = userRepository.save(createValidUserToBeSaved(roleRepository));

        Optional<User> userOpt = userRepository.findByUsername(savedUser.getUsername());

        assertThat(userOpt).isPresent().isNotNull().contains(savedUser);

    }

    @Test
    void existsByUsername_ReturnsTrue_WhenSuccessful() {

        User savedUser = userRepository.save(createValidUserToBeSaved(roleRepository));

        boolean exists = userRepository.existsByUsername(savedUser.getUsername());

        assertThat(exists).isTrue();

    }
}