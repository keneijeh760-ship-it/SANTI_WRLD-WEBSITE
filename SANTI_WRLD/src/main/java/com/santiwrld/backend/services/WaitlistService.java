package com.santiwrld.backend.services;

import com.santiwrld.backend.entities.WaitlistEntry;
import com.santiwrld.backend.repositories.WaitlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WaitlistService {

    private final WaitlistRepository waitlistRepository;
    public void addEmail(String email) {
        if (waitlistRepository.existsByEmail(email)) {
            throw new IllegalStateException("This email is already on the waitlist");
        }
        WaitlistEntry entry = new WaitlistEntry();
        entry.setEmail(email);
        waitlistRepository.save(entry);
    }

    public WaitlistEntry getAllWaitlistEntry() {
        return waitlistRepository.findAll()
                .stream()
                .findFirst()
                .orElse(null);
    }

    public void removeByEmail(String email) {
        boolean waitlistEntry = waitlistRepository.existsByEmail(email);

        WaitlistEntry emailtest = waitlistRepository.findByEmail(email)
                .orElse(null);

        if (waitlistEntry && emailtest != null) {
            waitlistRepository.delete(emailtest);
        }
    }
}
