package tr.com.obss.jip.springdemo.service;

import tr.com.obss.jip.springdemo.model.Role;
import tr.com.obss.jip.springdemo.model.RoleType;

import java.util.List;

public interface RoleService {
    Role findByName(RoleType roleType);

    void createNewRole(Role role);

    List<Role> getAllRoles();
}
