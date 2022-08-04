package tr.com.obss.jip.bookportal.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tr.com.obss.jip.bookportal.model.Book;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    void deleteBookById(Long id);

    Book findBookById(Long id);

    List<Book> findBooksByName(String name);
}
