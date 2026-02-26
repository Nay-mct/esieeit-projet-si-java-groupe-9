package com.esieeit.projetsi.infrastructure.repository;

import com.esieeit.projetsi.domain.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
