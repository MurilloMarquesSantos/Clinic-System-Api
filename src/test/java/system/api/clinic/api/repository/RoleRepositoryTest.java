package system.api.clinic.api.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import system.api.clinic.api.domain.Roles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void findByName_ReturnsOptionalOfRoles_WhenSuccessful() {

        Roles role = roleRepository.save(Roles.builder().name("ADMIN").build());

        Optional<Roles> roleOpt = roleRepository.findByName(role.getName());


        assertThat(roleOpt).isPresent();

        assertThat(roleOpt.get()).isNotNull().isEqualTo(role);


    }

}