package cz.cvut.fel.ear.pujcovna.repository;

import cz.cvut.fel.ear.pujcovna.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Roles, Long> {
    Roles findByName(String name);
}
