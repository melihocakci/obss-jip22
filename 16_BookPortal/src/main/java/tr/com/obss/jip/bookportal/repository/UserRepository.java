package tr.com.obss.jip.bookportal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tr.com.obss.jip.bookportal.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  User findUserByUsername(String username);

  User findUserById(Long id);

  Page<User> findAllByUsernameContaining(Pageable pageable, String username);

  @Transactional
  @Modifying
  @Query(
      value =
          "delete from read_list where user_id = :id ;"
              + "delete from favorite_list where user_id = :id ;"
              + "delete from users where id = :id ;",
      nativeQuery = true)
  void deleteUserById(@Param("id") Long id);
}
