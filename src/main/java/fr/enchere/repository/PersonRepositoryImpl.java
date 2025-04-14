package fr.enchere.repository;

import fr.enchere.model.Person;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonRepositoryImpl implements PersonRepository {

    @Override
    public List<Person> getAllPerson() {
        return List.of();
    }
}
