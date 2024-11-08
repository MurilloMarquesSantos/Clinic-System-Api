package system.api.clinic.api.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import system.api.clinic.api.domain.Doctor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static system.api.clinic.api.util.doctor.DoctorCreator.createValidDoctor;
import static system.api.clinic.api.util.doctor.DoctorCreator.createValidDoctorToBeSaved;

@DataJpaTest
class DoctorRepositoryTest {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Test
    void findAll_ReturnsListOfDoctor_WhenSuccessful() {

        Doctor savedDoc = doctorRepository.save(createValidDoctorToBeSaved(scheduleRepository));

        List<Doctor> doctorList = doctorRepository.findAll();

        assertThat(doctorList).isNotEmpty().contains(savedDoc);

    }

    @Test
    void findByName_ReturnsListOfDoctor_WhenSuccessful() {

        Doctor savedDoc = doctorRepository.save(createValidDoctorToBeSaved(scheduleRepository));

        List<Doctor> doctors = doctorRepository.findByName(savedDoc.getName());

        assertThat(doctors).hasSize(1);

        assertThat(doctors.get(0)).isNotNull().isEqualTo(savedDoc);
    }

    @Test
    void findBySpecialty_ReturnsListOfDoctor_WhenSuccessful() {

        Doctor savedDoc = doctorRepository.save(createValidDoctorToBeSaved(scheduleRepository));

        List<Doctor> doctors = doctorRepository.findBySpecialty(savedDoc.getSpecialty());

        assertThat(doctors).hasSize(1);

        assertThat(doctors.get(0)).isNotNull().isEqualTo(savedDoc);
    }

    @Test
    void existsByNameAndSpecialtyAndEmail_ReturnsTrue_WhenSuccessful() {

        Doctor savedDoc = doctorRepository.save(createValidDoctorToBeSaved(scheduleRepository));

        boolean exists = doctorRepository.existsByNameAndSpecialtyAndEmail(
                savedDoc.getName(), savedDoc.getSpecialty(), savedDoc.getEmail());

        assertThat(exists).isTrue();

    }

    @Test
    void save_PersistsDoctor_WhenSuccessful() {

        Doctor savedDoc = doctorRepository.save(createValidDoctorToBeSaved(scheduleRepository));

        assertThat(savedDoc.getName()).isNotNull().isEqualTo(createValidDoctor().getName());

        assertThat(savedDoc.getId()).isEqualTo(createValidDoctor().getId());

        assertThat(savedDoc.getSpecialty()).isNotNull().isEqualTo(createValidDoctor().getSpecialty());


    }
}