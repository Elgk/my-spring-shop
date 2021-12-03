package ru.geekbrains.myspringshop.frontend;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import ru.geekbrains.myspringshop.entity.Product;
import ru.geekbrains.myspringshop.entity.Review;
import ru.geekbrains.myspringshop.service.ProductService;
import ru.geekbrains.myspringshop.service.ReviewService;
import ru.geekbrains.myspringshop.util.PersonUtil;

import java.util.UUID;


@Route("add-review/:productId")
public class AddReviewView extends AbstractView implements BeforeEnterObserver {
    private final ReviewService reviewService;
    private final ProductService productService;
    private UUID productId;

    public AddReviewView(ReviewService reviewService, ProductService productService){
        this.reviewService = reviewService;
        this.productService = productService;
        initAddReviewView();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        productId = UUID.fromString(event.getRouteParameters().get("productId").get());
    }

    private void initAddReviewView() {
        HorizontalLayout titleLayout = new HorizontalLayout();
        HorizontalLayout buttonLayout = new HorizontalLayout();

        Product product = productService.getProductById(productId);
        var productTitle = new Label(product.getName() +", "+ product.getPrice());

        var contentTextField = initTextFieldWithPlaceholder("product review");
        contentTextField.setSizeUndefined();
        var saveButton = new Button("Сохранить", e ->{
            var review = new Review();
            review.setProduct(product);
            review.setContent(contentTextField.getValue());
            review.setModerated(false);
            review.setPerson(PersonUtil.getCurrentPerson());
            reviewService.save(review);
            Notification.show("Отзыв успешно добавлен.");
        });

        titleLayout.add(productTitle);
        buttonLayout.add(saveButton);
        add(titleLayout, contentTextField, buttonLayout);
    }
}
