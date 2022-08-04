package tr.com.obss.jip.bookportal.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tr.com.obss.jip.bookportal.model.Book;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    Book findBookById(Long id);

    List<Book> findBooksByName(String name);

    @Transactional
    void deleteBookById(Long id);
}
