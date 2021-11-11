package ru.geekbrains.myspringshop.frontend;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import ru.geekbrains.myspringshop.entity.Order;
import ru.geekbrains.myspringshop.entity.Product;
import ru.geekbrains.myspringshop.service.OrderService;
import ru.geekbrains.myspringshop.util.PersonUtil;

import java.util.Optional;

@Route("my-orders")
public class OrderView extends AbstractView{
    private  final OrderService orderService;
    private final Grid<Order> orderGrid = new Grid<>(Order.class);

    public OrderView(OrderService orderService){

        this.orderService = orderService;
        initPage();
    }

    private void initPage() {
        Text titleText = new Text("Мои заказы");

        orderGrid.setSizeUndefined();
        var personOrders = orderService.findOrdersByPerson();

        orderGrid.setItems(personOrders);
        orderGrid.setColumns("createdAt", "cost", "address");
        ListDataProvider<Order> dataProvider = DataProvider.ofCollection(personOrders);
        orderGrid.setDataProvider(dataProvider);
        add(titleText, orderGrid);
    }
}
