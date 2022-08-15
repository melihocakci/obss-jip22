package tr.com.obss.jip.bookportal.service;

import tr.com.obss.jip.bookportal.model.Role;
import tr.com.obss.jip.bookportal.other.RoleType;

import java.util.List;

public interface RoleService {

    void createRole(Role role);

    List<Role> getRoles();

    Role getRole(RoleType name);
}
