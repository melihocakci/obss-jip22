package tr.com.obss.jip.bookportal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tr.com.obss.jip.bookportal.model.Book;

@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {

    @Query(
            value =
                    "select * from books "
                            + "where deleted = false"
                            + " and (lower(name) like lower('%'||:searchParam||'%')"
                            + " or lower(author) like lower('%'||:searchParam||'%'))",
            nativeQuery = true)
    Page<Book> search(Pageable pageable, @Param("searchParam") String searchParam);
}
