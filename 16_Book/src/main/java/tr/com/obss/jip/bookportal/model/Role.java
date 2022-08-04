package tr.com.obss.jip.bookportal.model;

import lombok.Getter;
import lombok.Setter;
import tr.com.obss.jip.bookportal.mapper.RoleType;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @SequenceGenerator(name = "roles_seq_gen", sequenceName = "roles_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_seq_gen")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private RoleType name;
}
