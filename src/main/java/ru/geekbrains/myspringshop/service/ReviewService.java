package ru.geekbrains.myspringshop.service;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.geekbrains.myspringshop.config.security.CustomUserDetails;
import ru.geekbrains.myspringshop.config.security.CustomUserDetailsService;
import ru.geekbrains.myspringshop.entity.Person;
import ru.geekbrains.myspringshop.entity.Product;
import ru.geekbrains.myspringshop.entity.Review;
import ru.geekbrains.myspringshop.entity.repository.ReviewRepository;
import ru.geekbrains.myspringshop.frontend.AddProductView;
import ru.geekbrains.myspringshop.util.PersonUtil;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Optional;



@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;


    public ReviewService(ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
    }

    public List<Review> findAllModeratedIsFalse(){
        return reviewRepository.findAllByModeretedIsFalse();
    }

    public List<Review> findAllByProduct(Product product){
        return reviewRepository.findAllByProductAndModeratedIsTrue(product);
    }

    public Review save(Review review){
        return reviewRepository.save(review);
    }
}
