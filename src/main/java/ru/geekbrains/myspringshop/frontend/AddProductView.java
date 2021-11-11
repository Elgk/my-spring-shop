package ru.geekbrains.myspringshop.frontend;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import ru.geekbrains.myspringshop.entity.Product;
import ru.geekbrains.myspringshop.service.ProductService;

//import java.awt.*;
import java.math.BigDecimal;


@Route("add-product")
public class AddProductView extends AbstractView{
    private final ProductService productService;

    public AddProductView(ProductService productService){
        this.productService = productService;
        initAddProductView();
    }

    private void initAddProductView() {
        var nameTextField = initTextFieldWithPlaceholder("Product name");
        var priceTextField = initTextFieldWithPlaceholder("Price");
        var countTextField = initTextFieldWithPlaceholder("Quontity");
        var vendorCodeTextField = initTextFieldWithPlaceholder("Vendor code");
        var createButton = new Button("Add product", e ->{
            boolean hasError = false;
            if (!nameTextField.getValue().matches("[а-яА-Я0-9]{1,128}+")){
                Notification.show("Наименование должно содержать буквы и цифры");
                hasError = true;
            }
            if (!priceTextField.getValue().matches("[0-9]+")){   // + означает что можно неоднократно вводить символ
                Notification.show("Цена должна состоять только из цифр");
                hasError = true;
            }if (!countTextField.getValue().matches("[0-9]+")){
                Notification.show("Количество должно состоять только из цифр");
                hasError = true;
            }if (!vendorCodeTextField.getValue().matches("[0-9]+")){
                Notification.show("Артикул должен состоять только из цифр");
                hasError = true;
            }

            if (hasError){
                return;
            }
            var newProduct = new Product();
            newProduct.setName(nameTextField.getValue());
            newProduct.setPrice(new BigDecimal(priceTextField.getValue()));
            newProduct.setCount(Integer.parseInt(countTextField.getValue()));
            newProduct.setVendorCode(vendorCodeTextField.getValue());
            productService.save(newProduct);
            Notification.show("Продукт успешно добавлен");

            nameTextField.clear();
            priceTextField.clear();
            countTextField.clear();
            vendorCodeTextField.clear();
        });
        add(nameTextField, priceTextField, countTextField, vendorCodeTextField, createButton);
    }


}
