package ru.geekbrains.myspringshop.frontend;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import ru.geekbrains.myspringshop.entity.Cart;
import ru.geekbrains.myspringshop.service.CartService;

@Route("cart")
public class CartView extends  AbstractView{
    private final Grid<Cart.InnerProduct> cartGrid = new Grid<>(Cart.InnerProduct.class);

    private final CartService cartService;

    public CartView( CartService cartService) {
        this.cartService = cartService;
        initPage();
    }

    private void initPage() {
        initCartGrid();
        add(cartGrid);
    }

    private void initCartGrid(){
        var cart = cartService.findByPerson(cartService.findPerson());

        var products = cart.getProducts();
        cartGrid.setItems(products);
        cartGrid.setColumns("name","price","count","vendorCode");
        cartGrid.setSizeUndefined();
        ListDataProvider<Cart.InnerProduct> dataProvider = DataProvider.ofCollection(products);
        cartGrid.setDataProvider(dataProvider);

    }
}
