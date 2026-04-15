package com.santiwrld.backend.services;

import com.santiwrld.backend.ExceptionHandlers.ResourceNotFound;
import com.santiwrld.backend.entities.User;
import com.santiwrld.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findByEmail (String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new ResourceNotFound("User not found"));

        return user;
    }



}
