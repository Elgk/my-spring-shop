package ru.geekbrains.myspringshop.entity.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.geekbrains.myspringshop.entity.Cart;
import ru.geekbrains.myspringshop.entity.Person;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends CrudRepository<Cart, UUID> {

    @Query("select c from Cart c " +
            "where c.person = :person and c.order is null")
    Optional<Cart> findCartByPerson(Person person);

}
