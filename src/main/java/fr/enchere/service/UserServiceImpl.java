package fr.enchere.service;

import fr.enchere.model.Person;
import fr.enchere.model.User;
import fr.enchere.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    public UserRepository userRepository;

    @Override
    public List<User> getAllPerson() {
        return List.of();
    }

    @Override
    public User getPersonByPseudo(String username) {
       User user = userRepository.findByPseudo(username);
        return user;
    }
}
