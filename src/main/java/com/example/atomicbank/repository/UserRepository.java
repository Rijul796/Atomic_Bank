package com.example.atomicbank.repository;

import com.example.atomicbank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// Extending JpaRepository gives us save(), delete(), findById(), etc. for FREE.
public interface UserRepository extends JpaRepository<User, Long> {

    // Spring Data JPA is smart. It reads this method name and writes the SQL for you.
    // SQL generated: SELECT * FROM users WHERE username = ?
    Optional<User> findByUsername(String username);
}