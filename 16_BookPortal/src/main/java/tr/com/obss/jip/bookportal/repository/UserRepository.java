package tr.com.obss.jip.bookportal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tr.com.obss.jip.bookportal.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query(
            value =
                    "select * from users "
                            + "where deleted = false"
                            + " and (lower(username) like lower('%'||:searchParam||'%') "
                            + " or lower(email) like lower('%'||:searchParam||'%'))",
            nativeQuery = true)
    Page<User> search(Pageable pageable, @Param("searchParam") String searchParam);

    @Query(value = "select * from users where username = :username", nativeQuery = true)
    User findAnyByUsername(@Param("username") String username);
}
