package tr.com.obss.jip.springdemo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tr.com.obss.jip.springdemo.model.Role;
import tr.com.obss.jip.springdemo.model.RoleType;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findRoleByName(RoleType roleType);
}
