package tr.com.obss.jip.bookportal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import tr.com.obss.jip.bookportal.model.Book;

@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {

    Page<Book> findAllByNameContaining(Pageable pageable, String name);
}
