package com.esieeit.projetsi.infrastructure.security;

import com.esieeit.projetsi.infrastructure.repository.UserJpaRepository;
import java.util.Locale;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserJpaRepository userRepository;

    public CustomUserDetailsService(UserJpaRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userRepository.findByEmailOrUsername(login.trim().toLowerCase(Locale.ROOT), login.trim())
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable: " + login));
    }
}
