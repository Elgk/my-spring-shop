package ru.geekbrains.myspringshop.service;

import org.springframework.stereotype.Service;
import ru.geekbrains.myspringshop.entity.Person;
import ru.geekbrains.myspringshop.entity.repository.PersonRepository;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository){
        this.personRepository = personRepository;
    }

    public Person save(Person person){
        return personRepository.save(person);
    }
}
