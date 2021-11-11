package ru.geekbrains.myspringshop.config.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.geekbrains.myspringshop.entity.Person;
import ru.geekbrains.myspringshop.service.PersonService;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final PersonService personService;

    public CustomUserDetailsService(PersonService personService){
        this.personService = personService;
    }

    // метод подгружает текущего пользователя, если он успешно прошел аутентифкацию, или ошибку, если не прошел
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personService.findByLogin(username);
        return new CustomUserDetails(person);
        // CustomUserDetails или CustomPrincipal - объект, который извлекает данные пользователя
    }
}
