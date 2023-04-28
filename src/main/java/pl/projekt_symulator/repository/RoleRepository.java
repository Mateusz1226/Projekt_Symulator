package pl.projekt_symulator.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.projekt_symulator.entity.Role;


public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}