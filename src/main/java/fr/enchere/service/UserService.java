package fr.enchere.service;

import fr.enchere.model.User;
import fr.enchere.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Service
public interface UserService {
    User updateUser (User user);
    User findByUsername(String username);
    User findByUserId(Long userId);
    User createUser(User user);
    int getProfileCompletion(Long userId);
    void deactivateUser(Long userId);
    String resetPassword(String email);
    void updatePassword(String token, String newPassword);
}

