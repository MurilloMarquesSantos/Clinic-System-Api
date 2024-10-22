package system.api.SystemApi.service;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import system.api.SystemApi.domain.Roles;
import system.api.SystemApi.repository.RoleRepository;

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