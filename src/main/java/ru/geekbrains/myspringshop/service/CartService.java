package ru.geekbrains.myspringshop.service;

import org.springframework.stereotype.Service;
import ru.geekbrains.myspringshop.aspect.Statistic;
import ru.geekbrains.myspringshop.entity.Cart;
import ru.geekbrains.myspringshop.entity.Person;
import ru.geekbrains.myspringshop.entity.repository.CartRepository;
import ru.geekbrains.myspringshop.entity.repository.PersonRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository, PersonRepository personRepository){
        this.cartRepository = cartRepository;
    }
    public Optional<Cart> findLastCart(Person person) {
        return cartRepository.findCartByPerson(person);
    }

    public Cart save(Cart cart){
        return cartRepository.save(cart);
    }
}
