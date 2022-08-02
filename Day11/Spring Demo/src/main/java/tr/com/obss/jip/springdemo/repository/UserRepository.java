package tr.com.obss.jip.springdemo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tr.com.obss.jip.springdemo.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {}