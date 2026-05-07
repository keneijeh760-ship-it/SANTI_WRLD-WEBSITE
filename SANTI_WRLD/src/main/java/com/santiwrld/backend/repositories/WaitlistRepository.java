package com.santiwrld.backend.repositories;

import com.santiwrld.backend.entities.WaitlistEntry;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WaitlistRepository extends JpaRepository<WaitlistEntry, Long> {
    Optional<WaitlistEntry> findByEmail(String email);

    boolean existsByEmail(String email);

}
