package system.api.clinic.api.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import system.api.clinic.api.domain.Schedule;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static system.api.clinic.api.util.schedule.ScheduleCreator.createSchedule;
import static system.api.clinic.api.util.schedule.ScheduleCreator.createScheduleToBeSaved;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ScheduleRepositoryTest {

    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    @Test
    void findById_ReturnsOptionalOfSchedule_WhenSuccessful() {

        Schedule savedSchedule = scheduleRepository.save(createScheduleToBeSaved());

        Optional<Schedule> scheduleOpt = scheduleRepository.findById(savedSchedule.getId());

        assertThat(scheduleOpt).isPresent();

        assertThat(scheduleOpt.get()).isNotNull().isEqualTo(createSchedule());
    }

    @Test
    void save_PersistsSchedule_WhenSuccessful() {

        Schedule savedSchedule = scheduleRepository.save(createScheduleToBeSaved());

        assertThat(savedSchedule).isNotNull().isEqualTo(createSchedule());


    }

    @Test
    void findByIdAndDoctorName_ReturnsOptionalOfSchedule_WhenSuccessful() {

        Schedule savedSchedule = scheduleRepository.save(createScheduleToBeSaved(doctorRepository));

        Optional<Schedule> scheduleOpt = scheduleRepository.
                findByIdAndDoctorName(savedSchedule.getId(), savedSchedule.getDoctor().getName());

        assertThat(scheduleOpt).isPresent();

        assertThat(scheduleOpt.get().getId()).isEqualTo(createSchedule().getId());

        assertThat(scheduleOpt.get().getDateTime()).isEqualTo(createSchedule().getDateTime());

        assertThat(scheduleOpt.get().getAvailable()).isEqualTo(createSchedule().getAvailable());


    }
}