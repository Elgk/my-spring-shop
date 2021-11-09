package ru.geekbrains.myspringshop.entity.repository;


import org.springframework.data.repository.CrudRepository;
import ru.geekbrains.myspringshop.entity.Cart;
import ru.geekbrains.myspringshop.entity.Person;

import java.util.UUID;

public interface CartRepository extends CrudRepository<Cart, UUID> {
    Cart findCartByPerson(Person person);
}
