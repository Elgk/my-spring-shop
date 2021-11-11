package ru.geekbrains.myspringshop.frontend;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import ru.geekbrains.myspringshop.aspect.StatisticAspect;
import ru.geekbrains.myspringshop.entity.Cart;
import ru.geekbrains.myspringshop.entity.Product;
import ru.geekbrains.myspringshop.entity.filter.ProductFilter;
import ru.geekbrains.myspringshop.service.CartService;
import ru.geekbrains.myspringshop.service.ProductService;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Route("main")
public class MainView extends AbstractView {
    private final Grid<Product> productGrid = new Grid<>(Product.class);
    private final TextField minPriceTextField = new TextField();
    private final TextField maxPriceTextField = new TextField();
    private final TextField nameProductTextField = new TextField();

    private final ProductService productService;

    private final CartService cartService;

    public MainView(ProductService productService, CartService cartService) {
        this.productService = productService;
        this.cartService = cartService;
        initPage();
    }

    private void initPage(){
        initProductGrid();
        add(productGrid, initFilterLayout(), initCartButton());
    }

    private HorizontalLayout initFilterLayout() {
        var hl = new HorizontalLayout();
        setAlignItems(Alignment.AUTO);
        setJustifyContentMode(JustifyContentMode.AROUND);

         minPriceTextField.setPlaceholder("Min price");
         maxPriceTextField.setPlaceholder("Max price");
         nameProductTextField.setPlaceholder("product title");

        var filterButton = new Button("Filtering", e ->{
            var map = new HashMap<String, String>();
            map.put("minPrice", minPriceTextField.getValue());
            map.put("maxPrice", maxPriceTextField.getValue());
            map.put("name", nameProductTextField.getValue());

            var productFilter = new ProductFilter(map);
            var filteredProductsList = productService.findAllByFilter(productFilter);
            ListDataProvider<Product> dataProvider = DataProvider.ofCollection(filteredProductsList);
            productGrid.setDataProvider(dataProvider);
        });

        var clearFilterButton = new Button("Cancel filter", e ->{
           minPriceTextField.clear();
           maxPriceTextField.clear();
           nameProductTextField.clear();
           var productsList = productService.findAll();
            ListDataProvider<Product> withoutFilterdataProvider = DataProvider.ofCollection(productsList);
            productGrid.setDataProvider(withoutFilterdataProvider);

        });
        add(minPriceTextField, maxPriceTextField, nameProductTextField, filterButton, clearFilterButton);

        return hl;

    }

    private void initProductGrid(){
        var products = productService.findAll();
        productGrid.setItems(products);
        productGrid.setColumns("name","price","count","vendorCode");
        productGrid.setSizeUndefined();
        productGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        ListDataProvider<Product> dataProvider = DataProvider.ofCollection(products);
        productGrid.setDataProvider(dataProvider);

        productGrid.addColumn(new ComponentRenderer<>(item ->{
            var plusButton = new Button(" + ", i -> {
            item.increaseCount();
            productService.save(item);
            productGrid.getDataProvider().refreshItem(item);
            });

            var minusButton = new Button(" - ", i ->{
                item.decreaseCount();
                productService.save(item);
                productGrid.getDataProvider().refreshItem(item);

            });
            return new HorizontalLayout(plusButton, minusButton);
        }));

    }

    private HorizontalLayout initCartButton(){
        var addToCartButton = new Button("Добавить в корзину", event -> {
            var cart = new Cart();
            List<Cart.InnerProduct> products = new ArrayList<>();
            Set<Product> gridSelectedItems = productGrid.getSelectedItems();
            for (Product product : gridSelectedItems) {
                Cart.InnerProduct innerProduct = new Cart.InnerProduct();
                innerProduct.setName(product.getName());
                innerProduct.setCount(product.getCount());
                innerProduct.setPrice(product.getPrice());
                innerProduct.setVendorCode(product.getVendorCode());
                products.add(innerProduct);
            }
            cart.setProducts(products);
            cart.setPerson(cartService.findPerson());
            cartService.save(cart);
            Notification.show("Товар успешно добавлен в корзину");
        });

//        var toCartViewButton = new Button("Корзина", event -> {
//            UI.getCurrent().navigate("cart");
//        });
        return new HorizontalLayout(addToCartButton);
    }
}
