package fr.enchere.service;

import fr.enchere.model.User;
import fr.enchere.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User updateUser (User user);
    User findByUsername(String username);
    User findByUserId(Long userId);
    User createUser(User user);
    int getProfileCompletion(Long userId);
    void deactivateUser(Long userId);
}

