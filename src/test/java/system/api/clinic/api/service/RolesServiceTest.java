package system.api.clinic.api.service;

import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import system.api.clinic.api.domain.Roles;
import system.api.clinic.api.repository.RoleRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@ExtendWith(SpringExtension.class)
class RolesServiceTest {

    @InjectMocks
    private RolesService rolesService;

    @Mock
    private RoleRepository roleRepositoryMock;

    @BeforeEach
    void setUp() {
        BDDMockito.when(roleRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(new Roles(1L, "ADMIN")));
    }

    @Test
    void getRoleByName_ReturnRoles_WhenSuccessful() throws BadRequestException {

        Roles role = rolesService.getRoleByName("");

        assertThat(role).isNotNull().isEqualTo(new Roles(1L, "ADMIN"));
    }

    @Test
    void getRoleByName_ThrowsBadRequest_WhenRoleIsNotFound() {
        BDDMockito.when(roleRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());

        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(()-> rolesService.getRoleByName(""))
                .withMessageContaining("This role does not exists");

    }
}