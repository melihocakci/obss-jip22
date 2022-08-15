package tr.com.obss.jip.bookportal.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tr.com.obss.jip.bookportal.exception.NotFoundException;
import tr.com.obss.jip.bookportal.model.Role;
import tr.com.obss.jip.bookportal.other.RoleType;
import tr.com.obss.jip.bookportal.repository.RoleRepository;
import tr.com.obss.jip.bookportal.service.RoleService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public Role getRole(RoleType roleType) {
        Optional<Role> optionalRole = roleRepository.findByName(roleType);

        if (optionalRole.isEmpty()) {
            throw new NotFoundException("Role does not exist");
        }

        return optionalRole.get();
    }

    @Override
    public void createRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public List<Role> getRoles() {
        return (List<Role>) roleRepository.findAll();
    }
}
