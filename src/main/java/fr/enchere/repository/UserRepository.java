package fr.enchere.repository;

import fr.enchere.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByUserId(Long userId);
    List<User> findByActiveTrue();
    User findByEmail(String email);
    User findByResetToken(String resetToken);
}
