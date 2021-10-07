package ru.geekbrains.myspringshop.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.geekbrains.myspringshop.entity.Cart;
import ru.geekbrains.myspringshop.entity.Person;
import ru.geekbrains.myspringshop.entity.Product;
import ru.geekbrains.myspringshop.entity.repository.CartRepository;
import ru.geekbrains.myspringshop.entity.repository.PersonRepository;
import ru.geekbrains.myspringshop.entity.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class Runner implements CommandLineRunner {
    @Autowired
    private  ProductRepository productRepository;

    @Autowired
    private  PersonRepository personRepository;

    @Autowired
    private  CartRepository cartRepository;


    @Override
    public void run(String... args) throws Exception {
     /*   var person = new Person();
        person.setFirstName("Elena");
        person.setLastName("Kuznetsova");
        person.setPassword("2222");
        person.setPhone("5022");
        personRepository.save(person);

        var cart = new Cart();
        List<Cart.InnerProduct> products = new ArrayList<>();
        Cart.InnerProduct product = new Cart.InnerProduct();
        product.setName("milk");
        product.setPrice(BigDecimal.valueOf(70));
        product.setCount(1);
        products.add(product);
        Cart.InnerProduct product2 = new Cart.InnerProduct();
        product2.setName("cheese");
        product2.setPrice(BigDecimal.valueOf(700));
        product2.setCount(1);
        products.add(product2);

        cart.setProducts(products);
        cart.setPerson(person);
        cartRepository.save(cart);
        System.out.println(cart.getProducts());
*/
        Product product = new Product();
        product.setCreatedBy(personRepository.findById(UUID.fromString("14983a99-cc7a-4e49-a670-7d4145a7b38d")).get());
        product.setCount(1);
        product.setPrice(BigDecimal.valueOf(700));
        product.setName("cheese");
        product.setVendorCode("1234");
        productRepository.save(product);
    }
}
