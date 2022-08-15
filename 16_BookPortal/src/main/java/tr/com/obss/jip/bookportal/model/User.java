package tr.com.obss.jip.bookportal.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

  @Id
  @SequenceGenerator(name = "users_seq_gen", sequenceName = "users_id_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq_gen")
  private Long id;

  @Column(unique = true, nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
  private Role role;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "read_list",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "book_id"))
  private List<Book> readBooks = new ArrayList<>();

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "favorite_list",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "book_id"))
  private List<Book> favoriteBooks = new ArrayList<>();
}
