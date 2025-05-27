package cz.cvut.fel.ear.pujcovna;

import cz.cvut.fel.ear.pujcovna.model.Category;
import cz.cvut.fel.ear.pujcovna.model.Product;
import cz.cvut.fel.ear.pujcovna.model.Roles;
import cz.cvut.fel.ear.pujcovna.model.User;
import cz.cvut.fel.ear.pujcovna.repository.CategoryRepository;
import cz.cvut.fel.ear.pujcovna.repository.ProductRepository;
import cz.cvut.fel.ear.pujcovna.repository.RolesRepository;
import cz.cvut.fel.ear.pujcovna.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Set;

@Configuration
public class DatabaseInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public DatabaseInitializer(UserRepository userRepository, RolesRepository rolesRepository, CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (rolesRepository.findByName("ROLE_USER") != null) {
            // Database is persistent and already setup
            return;
        }
        Roles userRole = new Roles("ROLE_USER");
        Roles adminRole = new Roles("ROLE_ADMIN");

        rolesRepository.save(userRole);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User admin = new User(
                "admin",
                passwordEncoder.encode("admin"),
                "admin@admin.admin",
                "admin",
                "Admin street",
                "+420 420 420 420",
                Set.of(adminRole));

        userRepository.save(admin);
    }
}
