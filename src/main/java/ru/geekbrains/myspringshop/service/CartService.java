package ru.geekbrains.myspringshop.service;

import org.springframework.stereotype.Service;
import ru.geekbrains.myspringshop.aspect.Statistic;
import ru.geekbrains.myspringshop.entity.Cart;
import ru.geekbrains.myspringshop.entity.Person;
import ru.geekbrains.myspringshop.entity.repository.CartRepository;
import ru.geekbrains.myspringshop.entity.repository.PersonRepository;

import java.util.UUID;

@Service
public class CartService {
    private static String PERSON_ID = "14983a99-cc7a-4e49-a670-7d4145a7b38d";
    private final CartRepository cartRepository;
    private final PersonRepository personRepository;

    public CartService(CartRepository cartRepository, PersonRepository personRepository){
        this.cartRepository = cartRepository;
        this.personRepository = personRepository;
    }

    public Cart findByPerson(Person person){
        return  cartRepository.findCartByPerson(person);
    }

    public Person findPerson(){
        return personRepository.findById(UUID.fromString(PERSON_ID)).get();
    }

    @Statistic
    public Cart save(Cart cart){
        return cartRepository.save(cart);
    }
}
