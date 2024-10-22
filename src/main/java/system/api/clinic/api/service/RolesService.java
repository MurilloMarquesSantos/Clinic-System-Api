package system.api.clinic.api.service;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import system.api.clinic.api.domain.Roles;
import system.api.clinic.api.repository.RoleRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RolesService {

    private final RoleRepository roleRepository;

    public Roles getRoleByName(String name) throws BadRequestException {
        Optional<Roles> role = roleRepository.findByName(name);
        if (role.isEmpty()) {
            throw new BadRequestException("This role does not exists");
        }
        return role.get();
    }

}