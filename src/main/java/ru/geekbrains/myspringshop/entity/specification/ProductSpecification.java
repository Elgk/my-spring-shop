package ru.geekbrains.myspringshop.entity.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.myspringshop.entity.Product;

import java.math.BigDecimal;

public class ProductSpecification {
    public static Specification<Product> priceGreaterOrEqualsThen(BigDecimal minPrice){
        return ((root, query, criteriaBuilder) ->
            criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice)
        );
    }

    public static Specification<Product> priceLessOrEqualsThen(BigDecimal maxPrice){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("price"), maxPrice)
        );
    }

    public static Specification<Product> productNameLike(String name){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), String.format("%%%s%%", name))
        );
    }
}
