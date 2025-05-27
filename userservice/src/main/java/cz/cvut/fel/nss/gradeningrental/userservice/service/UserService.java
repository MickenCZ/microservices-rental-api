package cz.cvut.fel.nss.gradeningrental.userservice.service;

import cz.cvut.fel.nss.gradeningrental.userservice.model.User;
import cz.cvut.fel.nss.gradeningrental.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserById(Long id) {
        return Optional.of(userRepository.getReferenceById(id));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
