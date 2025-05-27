package cz.cvut.fel.nss.gradeningrental.userservice.controller;

import cz.cvut.fel.nss.gradeningrental.userservice.DTO.LoginRequestDTO;
import cz.cvut.fel.nss.gradeningrental.userservice.DTO.AuthResponseDTO;
import cz.cvut.fel.nss.gradeningrental.userservice.DTO.RegisterRequestDTO;
import cz.cvut.fel.nss.gradeningrental.userservice.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Handles user login requests.
     *
     * @param loginRequestDTO the login request containing username and password
     * @return a response entity containing the authentication token if login is successful
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        AuthResponseDTO resp = authService.authenticate(loginRequestDTO);
        return ResponseEntity.ok(resp);
    }

    /**
     * Handles user registration requests.
     *
     * @param registerRequestDTO the login request containing username and password
     * @return a response entity containing the authentication token upon successful registration
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        AuthResponseDTO loginResponseDTO = authService.register(
                registerRequestDTO.getUsername(),
                registerRequestDTO.getPassword(),
                registerRequestDTO.getEmail(),
                registerRequestDTO.getFullName(),
                registerRequestDTO.getPhoneNumber(),
                registerRequestDTO.getCountry(),
                registerRequestDTO.getCity(),
                registerRequestDTO.getStreet(),
                registerRequestDTO.getStreetNumber(),
                registerRequestDTO.getPostalCode()
                );
        return ResponseEntity.ok(loginResponseDTO);
    }
}
