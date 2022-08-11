package tr.com.obss.jip.bookportal.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tr.com.obss.jip.bookportal.model.Book;

@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {

    Book findBookById(Long id);

    @Transactional
    void deleteBookById(Long id);
}
