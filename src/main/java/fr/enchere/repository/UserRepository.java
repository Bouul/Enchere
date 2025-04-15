package fr.enchere.repository;

import fr.enchere.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Trouver un utilisateur par son pseudo (username)
    User findByUsername(String username);
}
