package tr.com.obss.jip.springdemo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tr.com.obss.jip.springdemo.model.User;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findUsersByName(String name);

    User findUserById(Long id);
}