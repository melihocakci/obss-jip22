package tr.com.obss.jip.bookportal.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tr.com.obss.jip.bookportal.model.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findUserByUsername(String username);

    User findUserById(Long id);

    @Transactional
    void deleteUserById(Long id);

    @Transactional
    void deleteUserByUsername(String username);

}
