package ru.geekbrains.myspringshop.entity.repository;

import org.springframework.data.repository.CrudRepository;
import ru.geekbrains.myspringshop.entity.Product;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends CrudRepository<Product, UUID> {
    List<Product> findAll();
}