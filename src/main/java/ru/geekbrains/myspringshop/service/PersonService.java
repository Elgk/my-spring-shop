package ru.geekbrains.myspringshop.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.geekbrains.myspringshop.entity.Person;
import ru.geekbrains.myspringshop.entity.repository.PersonRepository;
import ru.geekbrains.myspringshop.integration.KeycloakIntegration;
import ru.geekbrains.myspringshop.util.DecodeJwtToken;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final KeycloakIntegration keycloakIntegration;


    public PersonService(PersonRepository personRepository,
                         PasswordEncoder passwordEncoder,
                         KeycloakIntegration keycloakIntegration){
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
        this.keycloakIntegration = keycloakIntegration;
    }

    public Person findByLogin(String login) {
        return personRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден по логину"));
    }

    public Person findByKeycloakId(UUID keycloakId) {
        return personRepository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден по логину"));
    }

    @Transactional(rollbackOn = Exception.class)
    public Person save(Person person) {
        keycloakIntegration.createUser(person);
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setKeycloakId(UUID.fromString((String) DecodeJwtToken.decode("sub")));
        return personRepository.save(person);
    }


}
