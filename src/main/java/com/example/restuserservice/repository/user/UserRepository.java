package com.example.restuserservice.repository.user;

import com.example.restuserservice.models.User;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findById(Long userId);

    List<User> findByBirthDateBetween(LocalDate from, LocalDate to);
}
