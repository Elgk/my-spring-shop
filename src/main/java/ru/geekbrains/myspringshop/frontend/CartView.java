package ru.geekbrains.myspringshop.frontend;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import ru.geekbrains.myspringshop.entity.Cart;
import ru.geekbrains.myspringshop.entity.Order;
import ru.geekbrains.myspringshop.service.CartService;
import ru.geekbrains.myspringshop.service.OrderService;
import ru.geekbrains.myspringshop.util.PersonUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;
import java.util.stream.Collectors;

@Route("cart")
public class CartView extends  AbstractView{
    private final Grid<Cart.InnerProduct> cartGrid = new Grid<>(Cart.InnerProduct.class);
    private final Cart cart;
    private final CartService cartService;
    private  final OrderService orderService;

    public CartView( CartService cartService,
                     OrderService orderService) {
        this.cartService = cartService;
        this.orderService = orderService;

        Optional<Cart> optionalCart = cartService.findLastCart(PersonUtil.getCurrentPerson());
        this.cart = optionalCart.orElse(null);

        initPage();
    }

    private void initPage() {
        initCartGrid();
        initButtons();
        add(cartGrid);
    }

    private void initButtons() {
        var saveButton = new Button("Сохранить", e -> {
            var innerProducts = cartGrid.getDataProvider()
                    .fetch(new Query<>()).collect(Collectors.toList());

            cart.setProducts(innerProducts);
            cartService.save(cart);
        });

        var initOrderButton = new Button("Создать заказ", e -> {
            var order = new Order();
            order.setCart(cart);
            order.setAddress(PersonUtil.getCurrentPerson().getAddress());
            order.setCost(calculateCost());
            order.setPerson(PersonUtil.getCurrentPerson());

            order = orderService.save(order);

            cart.setOrder(order);

            cartService.save(cart);
        });

        add(saveButton, initOrderButton);
    }

    private BigDecimal calculateCost() {
        var cost = new BigDecimal(BigInteger.ZERO);
        for (Cart.InnerProduct innerProduct : cart.getProducts()) {
            cost = cost.add(innerProduct.getPrice().multiply(BigDecimal.valueOf(innerProduct.getCount())));
        }
        return cost;
    }

    private void initCartGrid(){
        cartGrid.setSizeUndefined();
        cartGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        if (cart != null){
            cartGrid.setColumns("name","price","count","vendorCode");
            var products = cart.getProducts();
            cartGrid.setItems(products);
            ListDataProvider<Cart.InnerProduct> dataProvider = DataProvider.ofCollection(products);
            cartGrid.setDataProvider(dataProvider);

            cartGrid.addColumn(new ComponentRenderer<>(item -> {
                var plusButton = new Button("+", i -> {
                    item.setCount(item.getCount() + 1);
                    cartGrid.getDataProvider().refreshItem(item);
                });

                var minusButton = new Button("-", i -> {
                    if (item.getCount() > 0) {
                        item.setCount(item.getCount() - 1);
                        cartGrid.getDataProvider().refreshItem(item);
                    }
                });

                var deleteButton = new Button("Удалить", i -> {
                    var productList = cart.getProducts()
                            .stream()
                            .filter(p -> !p.getId().equals(item.getId()))
                            .collect(Collectors.toList());

                    cart.setProducts(productList);

                    cartService.save(cart);

                    UI.getCurrent().getPage().reload();
                });
                return new HorizontalLayout(plusButton, minusButton, deleteButton);
            }));
        }


    }
}
