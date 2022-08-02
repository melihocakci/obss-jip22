package tr.com.obss.jip.springdemo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "books")
public class Book {
    @Id
    @SequenceGenerator(name = "books_seq_gen", sequenceName = "books_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "books_seq_gen")
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String author;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Book)) {
            return false;
        } else {
            return id != null && id.equals(((Book) obj).getId());
        }
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
