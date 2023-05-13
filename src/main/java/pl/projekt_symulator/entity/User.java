package pl.projekt_symulator.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

@Table(name="users")
public class User
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String firstName;


    private String lastName;


    @Column(nullable=false, unique=true)
    private String email;


    @Column(nullable=false)

    private String password;

    @Column(name = "active")
    private Boolean active;


    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(
            name="users_roles",
            joinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="ID")})
    private List<Role> roles = new ArrayList<>();


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private MarketingData marketingData;

   // @OneToMany(mappedBy = "user")
  //  private List<Schedule> schedule;

    public User(String email) {
        this.email = email;
    }

    public User(long l, String email) {

    }
}