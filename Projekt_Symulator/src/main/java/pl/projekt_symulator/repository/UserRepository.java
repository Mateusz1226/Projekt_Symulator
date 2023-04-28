package pl.projekt_symulator.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.projekt_symulator.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

}