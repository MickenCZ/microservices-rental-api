package cz.cvut.fel.nss.gradeningrental.userservice.controller;

import cz.cvut.fel.nss.gradeningrental.userservice.DTO.UserDTO;
import cz.cvut.fel.nss.gradeningrental.userservice.model.User;
import cz.cvut.fel.nss.gradeningrental.userservice.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public List<UserDTO> getAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        return allUsers.stream().map(UserDTO::new).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return ResponseEntity.ok(new UserDTO(user));
    }
}
