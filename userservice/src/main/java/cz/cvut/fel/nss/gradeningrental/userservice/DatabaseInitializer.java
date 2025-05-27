package cz.cvut.fel.nss.gradeningrental.userservice;

import cz.cvut.fel.nss.gradeningrental.userservice.model.Address;
import cz.cvut.fel.nss.gradeningrental.userservice.model.Role;
import cz.cvut.fel.nss.gradeningrental.userservice.model.User;
import cz.cvut.fel.nss.gradeningrental.userservice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DatabaseInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public DatabaseInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("admin").isEmpty()) {
            Address address = new Address();
            address.setStreet("Main Street 123");
            address.setCity("Prague");
            address.setPostalCode("11000");
            address.setCountry("Czech Republic");
            address.setStreetNumber("102/19");

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin")); // encrypt password
            admin.setEmail("admin@example.com");
            admin.setFullName("Admin User");
            admin.setAddress(address);
            admin.setPhoneNumber("+420123456789");
            admin.setRoles(Set.of(Role.ROLE_ADMIN));

            userRepository.save(admin);
        }

        if (userRepository.findByUsername("employee").isEmpty()) {
            Address address = new Address();
            address.setStreet("Main Street 123");
            address.setCity("Prague");
            address.setPostalCode("11000");
            address.setCountry("Czech Republic");
            address.setStreetNumber("102/19");

            User employee = new User();
            employee.setUsername("employee");
            employee.setPassword(passwordEncoder.encode("employee")); // encrypt password
            employee.setEmail("employee@example.com");
            employee.setFullName("Employee User");
            employee.setAddress(address);
            employee.setPhoneNumber("+420123456789");
            employee.setRoles(Set.of(Role.ROLE_EMPLOYEE));

            userRepository.save(employee);
        }
    }
}
