package ru.geekbrains.myspringshop.frontend;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.geekbrains.myspringshop.config.security.CustomUserDetails;

import java.util.ArrayList;
import java.util.List;


public abstract class AbstractView extends VerticalLayout {
    private final Button logoutButton;
    private final Button mainPageButton;
    private final Button addProductPageButton;
    private final Button cartButton;

    private static final SimpleGrantedAuthority ADMIN_ROLE = new SimpleGrantedAuthority("admin");
    private static final SimpleGrantedAuthority MANAGER_ROLE = new SimpleGrantedAuthority("seller");
    private static final SimpleGrantedAuthority SELLER_ROLE = new SimpleGrantedAuthority("manager");
    private static final SimpleGrantedAuthority CUSTOMER_ROLE = new SimpleGrantedAuthority("customer");

    public AbstractView(){
       logoutButton = new Button("Exit", e -> {
           SecurityContextHolder.clearContext();
           UI.getCurrent().navigate(LoginView.class);
       });
        mainPageButton = new Button("Main product list", e ->{
            UI.getCurrent().navigate(MainView.class);
        });
        addProductPageButton = new Button("Add new product", e ->{
            UI.getCurrent().navigate(AddProductView.class);
        });
        cartButton = new Button("Bag", e ->{
            UI.getCurrent().navigate("cart");
        });

        cartButton.setVisible(false);

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        var hl = new HorizontalLayout(logoutButton, mainPageButton, cartButton);
        var adminButtons = initSpecialButtons();
        if (adminButtons != null && !adminButtons.isEmpty()){
            for (Button button : adminButtons) {
                hl.add(button);
            }
        }
        add(hl);
    }

    private List<Button> initSpecialButtons(){
        var details = SecurityContextHolder.getContext().getAuthentication().getDetails();
        var buttos = new ArrayList<Button>();

        if (details instanceof CustomUserDetails && ((CustomUserDetails) details).getAuthorities().contains(ADMIN_ROLE)){
            buttos.add(new Button("List of users", e ->
                UI.getCurrent().navigate(PersonListView.class)
            ));

            if (details instanceof CustomUserDetails){
                var detail = ((CustomUserDetails) details).getAuthorities();
                if (detail.contains(ADMIN_ROLE) || detail.contains(SELLER_ROLE) || detail.contains(MANAGER_ROLE)){
                    buttos.add(new Button("Add new product", e ->
                        UI.getCurrent().navigate(AddProductView.class)
                    ));
                }
            }
            // данный метод не вписывается по стилю, но внесен для демострации другого подхода
            if (details instanceof CustomUserDetails){
                var detail = ((CustomUserDetails) details).getAuthorities();
                if (detail.contains(ADMIN_ROLE) || detail.contains(CUSTOMER_ROLE) ){
                   cartButton.setVisible(true);
                }
            }
            return buttos;

        }
        return  null;
    }

    protected TextField initTextFieldWithPlaceholder(String placeString){
        var textField = new TextField();
        textField.setPlaceholder(placeString);
        return  textField;
    }
}
