package cz.cvut.fel.nss.gradeningrental.userservice.service;

import cz.cvut.fel.nss.gradeningrental.userservice.DTO.AuthResponseDTO;
import cz.cvut.fel.nss.gradeningrental.userservice.DTO.LoginRequestDTO;
import cz.cvut.fel.nss.gradeningrental.userservice.model.Address;
import cz.cvut.fel.nss.gradeningrental.userservice.model.Role;
import cz.cvut.fel.nss.gradeningrental.userservice.model.User;
import cz.cvut.fel.nss.gradeningrental.userservice.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {
    private final AuthenticationManager authManager;
    private final JWTService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final KafkaProducer kafkaProducer;

    public AuthService(AuthenticationManager authManager, JWTService jwtService, UserRepository userRepository, PasswordEncoder passwordEncoder, KafkaProducer kafkaProducer) {
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.kafkaProducer = kafkaProducer;
    }

    public AuthResponseDTO authenticate(LoginRequestDTO request) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = (User) auth.getPrincipal();
        // log when user logged in
        kafkaProducer.sendMessage("User", request.getUsername(), "logged in");
        String token = jwtService.generateToken(user);
        return new AuthResponseDTO(token);
    }

    public AuthResponseDTO register(String username, String password, String email, String fullName, String phoneNumber, String country, String city, String street, String streetNumber, String postalCode) {
        Address address = new Address(
                country,
                city,
                street,
                streetNumber,
                postalCode
        );

        User user = new User(
                username,
                passwordEncoder.encode(password),
                email,
                fullName,
                address,
                phoneNumber,
                Set.of(Role.ROLE_CUSTOMER)
        );

        userRepository.save(user);
        // log when new user account is created
        kafkaProducer.sendMessage("New User", username, "created");
        String token = jwtService.generateToken(user);
        return new AuthResponseDTO(token);
    }
}
