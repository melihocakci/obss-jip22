package tr.com.obss.jip.bookportal.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tr.com.obss.jip.bookportal.model.Role;
import tr.com.obss.jip.bookportal.other.RoleType;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
  Role findRoleByName(RoleType name);
}
