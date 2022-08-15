package tr.com.obss.jip.bookportal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tr.com.obss.jip.bookportal.model.Book;

@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {

  Book findBookById(Long id);

  @Transactional
  @Modifying
  @Query(
      value =
          "delete from read_list where book_id = :id ;"
              + "delete from favorite_list where book_id = :id ;"
              + "delete from books where id = :id ;",
      nativeQuery = true)
  void deleteBookById(@Param("id") Long id);

  Page<Book> findAllByNameContaining(Pageable pageable, String name);
}
