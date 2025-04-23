package fr.enchere.service;


import fr.enchere.model.User;
import fr.enchere.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User createUser(User user) {
        // Vérification que l'utilisateur n'est pas null
        if (user == null) {
            throw new IllegalArgumentException("L'utilisateur ne peut pas être null");
        }

        // Vérification que l'utilisateur n'existe pas déjà
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("Un utilisateur avec ce nom d'utilisateur existe déjà");
        }

        // Sauvegarde du nouvel utilisateur
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("L'utilisateur ne peut pas être null");
        }

        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByUserId(Long userId) {
        return userRepository.findByUserId(userId);
    }

    @Override
    public int getProfileCompletion(Long userId) {
        User user = findByUserId(userId);
        if (user == null) {
            throw new IllegalArgumentException("Utilisateur introuvable avec l'ID : " + userId);
        }
        return user.calculateProfileCompletion();
    }

    @Override
    public void deactivateUser(Long userId) {
        User user = userRepository.findByUserId(userId);
        if (user != null) {
            user.setActive(false);
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("Utilisateur introuvable avec l'ID : " + userId);
        }
    }

    @Override
    public String resetPassword(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("Aucun utilisateur trouvé avec cet email");
        }

        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusHours(1);

        user.setResetToken(token);
        user.setResetTokenExpiry(expiryDate);
        userRepository.save(user);

        return token;
    }


    public User validateResetToken(String token) {
        User user = userRepository.findByResetToken(token);
        if (user == null || user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Token invalide ou expiré");
        }
        return user;
    }

    @Override
    public void updatePassword(String token, String newPassword) {
        User user = validateResetToken(token);
        user.setPassword(newPassword); // Assurez-vous d'encoder le mot de passe
        user.setResetToken(null); // Supprimez le token après utilisation
        user.setResetTokenExpiry(null);
        userRepository.save(user);
    }
}
