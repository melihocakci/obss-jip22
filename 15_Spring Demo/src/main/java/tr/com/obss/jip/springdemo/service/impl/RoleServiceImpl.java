package tr.com.obss.jip.springdemo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tr.com.obss.jip.springdemo.model.Role;
import tr.com.obss.jip.springdemo.model.RoleType;
import tr.com.obss.jip.springdemo.repository.RoleRepository;
import tr.com.obss.jip.springdemo.service.RoleService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public Role findByName(RoleType roleType) {
        return roleRepository.findRoleByName(roleType);
    }

    @Override
    public void createNewRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public List<Role> getAllRoles() {
        return (List<Role>) roleRepository.findAll();
    }
}
