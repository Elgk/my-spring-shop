package ru.geekbrains.myspringshop.service;

import org.springframework.stereotype.Service;
import ru.geekbrains.myspringshop.entity.Product;
import ru.geekbrains.myspringshop.entity.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }
    public Product save(Product product){
       return productRepository.save(product);
    }
}
