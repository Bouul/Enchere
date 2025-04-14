package fr.enchere.service;

import fr.enchere.repository.PersonRepository;

public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void findAllPerson() {
        personRepository.getAllPerson();
    }
}
