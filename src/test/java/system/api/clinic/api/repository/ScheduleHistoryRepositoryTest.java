package system.api.clinic.api.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import system.api.clinic.api.domain.ScheduleHistory;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static system.api.clinic.api.util.schedule.ScheduleCreator.createScheduleHistory;
import static system.api.clinic.api.util.schedule.ScheduleCreator.createScheduleHistoryToBeSaved;
import static system.api.clinic.api.util.user.UserCreator.createValidUserToBeSaved;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ScheduleHistoryRepositoryTest {

    @Autowired
    private ScheduleHistoryRepository historyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Test
    void findByUserId_ReturnsPageOfScheduleHistory_WhenSuccessful() {

        userRepository.save(createValidUserToBeSaved(roleRepository));

        historyRepository.save(createScheduleHistoryToBeSaved());

        Page<ScheduleHistory> userHistory = historyRepository.findByUserId(
                1L, PageRequest.of(1, 1));

        assertThat(userHistory).isNotNull();

    }

    @Test
    void save_PersistsResetToken_WhenSuccessful() {

        userRepository.save(createValidUserToBeSaved(roleRepository));

        ScheduleHistory savedHistory = historyRepository.save(createScheduleHistoryToBeSaved());

        assertThat(savedHistory).isNotNull().isEqualTo(createScheduleHistory());
    }

    @Test
    void delete_RemovesHistory_WhenSuccessful() {

        userRepository.save(createValidUserToBeSaved(roleRepository));

        ScheduleHistory savedHistory = historyRepository.save(createScheduleHistoryToBeSaved());

        historyRepository.delete(savedHistory);

        Optional<ScheduleHistory> historyOpt = historyRepository.findById(savedHistory.getId());

        assertThat(historyOpt).isEmpty();
    }
}