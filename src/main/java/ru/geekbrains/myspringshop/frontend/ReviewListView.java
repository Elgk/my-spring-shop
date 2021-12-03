package ru.geekbrains.myspringshop.frontend;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.router.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.View;
import ru.geekbrains.myspringshop.config.security.CustomUserDetails;
import ru.geekbrains.myspringshop.entity.Review;
import ru.geekbrains.myspringshop.service.ProductService;
import ru.geekbrains.myspringshop.service.ReviewService;
import ru.geekbrains.myspringshop.util.PersonUtil;

import java.util.List;
import java.util.UUID;

@Route("reviews/:productId?")
public class ReviewListView extends AbstractView implements BeforeEnterObserver {
    private final ReviewService reviewService;
    private final ProductService productService;
    private final Grid<Review> reviewGrid = new Grid<>(Review.class);

    private UUID productId;

    public ReviewListView(ReviewService reviewService, ProductService productService){
        this.reviewService = reviewService;
        this.productService = productService;
        initPage();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String paramId = event.getRouteParameters().get("productId").orElse(null);
        if (!paramId.isEmpty()){
            productId = UUID.fromString(paramId);
        }
    }

    private void initPage() {
        HorizontalLayout titleLayout = new HorizontalLayout();
        Text titleText = new Text("Отзывы о товарах");

        var details = PersonUtil.getPersonDetails();
        List<Review> reviewList;
        if (details.contains(ADMIN_ROLE) || details.contains(MANAGER_ROLE)) {
            reviewList = reviewService.findAllModeratedIsFalse();
        }else {
            reviewList = reviewService.findAllByProduct(productService.getProductById(productId));
        }


        reviewGrid.setSizeUndefined();
        reviewGrid.setItems(reviewList);
        reviewGrid.addColumn("product.getName()").setHeader("Наименование продукта");
        reviewGrid.addColumn("product.getPrice()").setHeader("Цена");
        reviewGrid.addColumn("product.getVendorCode()").setHeader("Артикул");

        reviewGrid.addColumn("content").setHeader("Содержание");
        reviewGrid.addColumn("person.getLastName()").setHeader("Фамилия пользователя");
        reviewGrid.addColumn("person.getFirstName()").setHeader("Имя пользователя");
        reviewGrid.addColumn("createdAt").setHeader("Дата отзыва");

        ListDataProvider<Review> reviewDataProvider = DataProvider.ofCollection(reviewList);
        reviewGrid.setDataProvider(reviewDataProvider);

        if (details.contains(ADMIN_ROLE) || details.contains(MANAGER_ROLE)) {
            reviewGrid.addColumn(new ComponentRenderer<>(item -> {
                var moderate = new Checkbox(item.isModerated());
                moderate.addValueChangeListener((HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<Checkbox, Boolean>>)
                        checkboxBooleanComponentChangeEvent -> {
                            item.setModerated(moderate.getValue());
                            reviewService.save(item);
                        });
                return new HorizontalLayout(moderate);
            }));

        }
        titleLayout.add(titleText);
        add(titleLayout, reviewGrid);
    }
}
