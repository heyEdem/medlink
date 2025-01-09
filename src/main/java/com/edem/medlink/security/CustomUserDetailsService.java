package com.edem.medlink.security;

import com.edem.medlink.entities.User.User;
import com.edem.medlink.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByEmailAndVerified(username);
        return user.map(SecurityUser::new).orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }
}
