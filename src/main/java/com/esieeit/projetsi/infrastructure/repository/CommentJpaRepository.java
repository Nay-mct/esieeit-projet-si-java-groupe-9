package com.esieeit.projetsi.infrastructure.repository;

import com.esieeit.projetsi.domain.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<Comment, Long> {
}
