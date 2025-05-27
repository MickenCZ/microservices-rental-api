package cz.cvut.fel.ear.pujcovna.controller;

import cz.cvut.fel.ear.pujcovna.dto.UserRegistrationDTO;
import cz.cvut.fel.ear.pujcovna.model.Roles;
import cz.cvut.fel.ear.pujcovna.model.User;
import cz.cvut.fel.ear.pujcovna.repository.RolesRepository;
import cz.cvut.fel.ear.pujcovna.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
public class AuthController {
    private final UserRepository userRepository;
    private final RolesRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, RolesRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegistrationDTO registrationDTO) {
        if (userRepository.existsByUsername(registrationDTO.getUsername())) {
            return new ResponseEntity<>("Username already taken", HttpStatus.CONFLICT);
        }

        Set<Roles> roles = new HashSet<>();
        roles.add(roleRepository.findByName("ROLE_USER"));

        User user = new User(
                registrationDTO.getUsername(),
                passwordEncoder.encode(registrationDTO.getPassword()),
                registrationDTO.getEmail(),
                registrationDTO.getFullName(),
                registrationDTO.getAddress(),
                registrationDTO.getPhoneNumber(),
                roles
        );

        userRepository.save(user);
        return ResponseEntity.ok("User " + registrationDTO.getUsername() + " registered successfully");
    }


}
