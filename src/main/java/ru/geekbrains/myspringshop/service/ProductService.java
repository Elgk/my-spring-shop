package ru.geekbrains.myspringshop.service;

import org.springframework.stereotype.Service;
import ru.geekbrains.myspringshop.aspect.Statistic;
import ru.geekbrains.myspringshop.entity.Product;
import ru.geekbrains.myspringshop.entity.filter.ProductFilter;
import ru.geekbrains.myspringshop.entity.repository.ProductRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Statistic
    public List<Product> findAll(){
        return productRepository.findAll();
    }

    @Statistic
    public Product save(Product product){
       return productRepository.save(product);
    }

    public Product getProductById(UUID id) {
        return productRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<Product> findAllByFilter(ProductFilter filter){
       return productRepository.findAll(filter.getSpecification());
    }
}
