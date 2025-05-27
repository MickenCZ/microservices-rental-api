package cz.cvut.fel.ear.pujcovna.service.security;

import cz.cvut.fel.ear.pujcovna.model.User;
import cz.cvut.fel.ear.pujcovna.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with username " + username + " not found.");
        }
        GrantedAuthority authority;
        if (username.equals("admin")) {
            authority = new SimpleGrantedAuthority("ROLE_ADMIN");
        } else {
            authority = new SimpleGrantedAuthority("ROLE_USER");
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(Collections.singletonList(authority))
                .build();
    }
}
