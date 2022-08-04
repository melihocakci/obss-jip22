package tr.com.obss.jip.bookportal.service;

import tr.com.obss.jip.bookportal.RoleType;
import tr.com.obss.jip.bookportal.model.Role;

import java.util.List;

public interface RoleService {

    void createNewRole(Role role);

    List<Role> getAllRoles();

    Role findByName(RoleType name);
}
