package ru.geekbrains.myspringshop.entity.filter;

import antlr.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.myspringshop.entity.Product;
import ru.geekbrains.myspringshop.entity.specification.ProductSpecification;

import java.math.BigDecimal;
import java.util.Map;

public class ProductFilter {
    private Specification<Product> specification;

    public ProductFilter(Map<String, String> map){
        this.specification = Specification.where(null);

        var minPrice = map.get("minPrice");
        var maxPrice = map.get("maxPrice");
        var name = map.get("name");

        if (!minPrice.isEmpty()){
            var minPriceBigDecemal = new BigDecimal(minPrice);
            specification = specification.and(ProductSpecification.priceGreaterOrEqualsThen(minPriceBigDecemal));
        }
        if (!maxPrice.isEmpty()){
            var maxPriceBigDecemal = new BigDecimal(maxPrice);
            specification = specification.and(ProductSpecification.priceLessOrEqualsThen(maxPriceBigDecemal));
        }
        if (!name.isEmpty()){
            specification = specification.and(ProductSpecification.productNameLike(name));
        }
    }

    public Specification<Product> getSpecification(){
        return this.specification;
    }
}
