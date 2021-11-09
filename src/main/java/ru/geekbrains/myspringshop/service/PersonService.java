package ru.geekbrains.myspringshop.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.geekbrains.myspringshop.entity.Person;
import ru.geekbrains.myspringshop.entity.repository.PersonRepository;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    public PersonService(PersonRepository personRepository,
                         PasswordEncoder passwordEncoder){
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Person findByLogin(String login) {
        return personRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден по логину"));
    }

    public Person save(Person person){
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        return personRepository.save(person);
    }

}
