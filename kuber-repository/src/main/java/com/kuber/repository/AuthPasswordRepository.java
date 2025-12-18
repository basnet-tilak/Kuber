package com.kuber.repository;

import com.kuber.security.AuthPassword;
import com.kuber.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AuthPasswordRepository extends JpaRepository<AuthPassword, String> {
    Optional<AuthPassword> findByUserAndIsActiveTrue(UserAccount user);
}