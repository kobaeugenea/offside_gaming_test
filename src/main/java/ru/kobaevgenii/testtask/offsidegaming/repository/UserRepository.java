package ru.kobaevgenii.testtask.offsidegaming.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kobaevgenii.testtask.offsidegaming.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String username);
}
