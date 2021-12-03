package ru.geekbrains.myspringshop.entity.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.myspringshop.entity.Cart;
import ru.geekbrains.myspringshop.entity.Order;
import ru.geekbrains.myspringshop.entity.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {

   List<Order> findOrdersByPerson(Person person);
}
