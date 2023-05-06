package pl.projekt_symulator.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.projekt_symulator.entity.User;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    Optional<User> findById(Long id);



}