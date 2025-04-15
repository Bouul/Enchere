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
}
