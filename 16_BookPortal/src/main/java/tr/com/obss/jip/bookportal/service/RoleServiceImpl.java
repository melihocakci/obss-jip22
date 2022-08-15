package tr.com.obss.jip.bookportal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tr.com.obss.jip.bookportal.model.Role;
import tr.com.obss.jip.bookportal.other.RoleType;
import tr.com.obss.jip.bookportal.repository.RoleRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
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
