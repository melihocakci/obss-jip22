package tr.com.obss.jip.springdemo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @SequenceGenerator(name = "users_seq_gen", sequenceName = "users_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq_gen")
    @Column(name = "id", updatable = false)
    private Long id;

    @NotNull
    @Size(min = 5, max = 30)
    private String name;

    private Integer age;

    private String location;
}
