package com.csms.config;

import com.csms.entity.User;
import com.csms.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public DatabaseUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getStatus() == null || user.getStatus() != 0,
                true,
                true,
                true,
                authorities(user)
        );
    }

    private Collection<? extends GrantedAuthority> authorities(User user) {
        return List.of(new SimpleGrantedAuthority(user.getType() != null && user.getType() == 1 ? "ROLE_ADMIN" : "ROLE_STAFF"));
    }
}
