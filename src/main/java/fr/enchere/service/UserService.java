package fr.enchere.service;

import fr.enchere.model.User;
import fr.enchere.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Modification ici : String → Long
    public User getUserById(Long userId) {
        return userRepository.findByUserId(userId);
    }

    public void updateUser(User user) {
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            User existingUser = userRepository.findByUserId(user.getUserId());
            user.setPassword(existingUser.getPassword());
        }
        userRepository.save(user);
    }
}
