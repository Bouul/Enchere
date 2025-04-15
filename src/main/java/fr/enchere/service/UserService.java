package fr.enchere.service;

import fr.enchere.model.Person;
import fr.enchere.model.User;

import java.util.List;

public interface UserService {

    public List<User> getAllPerson();
    public User getPersonByPseudo(String username);

}
