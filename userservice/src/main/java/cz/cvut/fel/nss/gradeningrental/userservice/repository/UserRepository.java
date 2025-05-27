package cz.cvut.fel.nss.gradeningrental.userservice.repository;

import cz.cvut.fel.nss.gradeningrental.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(@Param("username") String username);
}
