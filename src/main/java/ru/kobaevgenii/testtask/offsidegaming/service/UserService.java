package ru.kobaevgenii.testtask.offsidegaming.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kobaevgenii.testtask.offsidegaming.domain.User;
import ru.kobaevgenii.testtask.offsidegaming.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Optional<User> getUser(String name) {
        return userRepository.findByName(name);
    }
}
