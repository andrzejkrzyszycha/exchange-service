package com.example.exchange.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User createUser(String firstName, String lastName) {
        // Additional validation if user exist in database - fe. pesel, date of birth - 400 bad request if user exists
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return user;
    }

    public User getUser(Long userId) {
        // Additional validation if user exists - 404 in case if not
        return userRepository.getReferenceById(userId);
    }

}
