package ru.geekbrains.myspringshop.entity.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.myspringshop.entity.*;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Integer> {

    List<Review> findAllByModeretedIsFalse();


    List<Review> findAllByModeretedIsTrue();

    List<Review> findAllByProductAndModeratedIsTrue(Product product);


}
