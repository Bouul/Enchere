package fr.enchere.service;

import fr.enchere.model.User;
import fr.enchere.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;  // Pour accéder à la base de données

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Récupérer un utilisateur par son pseudo (username)
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Mettre à jour un utilisateur
    public void updateUser(User user) {
        // Tu peux ajouter de la logique ici, comme vérifier les informations avant de les sauvegarder
        userRepository.save(user);  // Sauvegarde l'utilisateur mis à jour
    }
}
