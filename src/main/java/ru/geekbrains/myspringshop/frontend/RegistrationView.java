package ru.geekbrains.myspringshop.frontend;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import ru.geekbrains.myspringshop.entity.Person;
import ru.geekbrains.myspringshop.service.PersonService;


@Route("registration")
@PageTitle("Registration | Vaadin Shop")
public class RegistrationView extends VerticalLayout {

    private PersonService personService;

    public RegistrationView(PersonService personService){
        this.personService = personService;
        initRegistrationView();
    }

    private void initRegistrationView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        var loginTextField = initTextFieldWithPlaceholder("Логин");
      //  var passwordTextField = new PasswordField();
        var passwordTextField = initTextFieldWithPlaceholder("Пароль");
        var lastNameTextField = initTextFieldWithPlaceholder("Фамилия");
        var firstNameTextField = initTextFieldWithPlaceholder("Имя");
        var patronymicTextField = initTextFieldWithPlaceholder("Отчество");
        var phoneTextField = initTextFieldWithPlaceholder("Номер телефона");
       // var emailTextField = new EmailField();
        var emailTextField = initTextFieldWithPlaceholder("Эл. почта");
        var addressTextField = initTextFieldWithPlaceholder("Адрес");

//        passwordTextField.setPlaceholder("Пароль");
//        emailTextField.setPlaceholder("Эл. почта");

        var registrationButton = new Button("Зарегистрироваться", buttonClickEvent -> {
            boolean hasError = false;
            if (!phoneTextField.getValue().matches("\\d{7,14}+")){
                Notification.show("Телефон должен состоять из цифр");
                hasError = true;
            }
            if (!loginTextField.getValue().matches("[a-zA-Z0-9]{3,128}+")){
                Notification.show("Логин должен содержать только цифры и буквы [a-z, A-Z, 0-9]");
                hasError = true;
            }
            if (!passwordTextField.getValue().matches("[a-zA-Z0-9]{8,128}+")){
                Notification.show("Пароль должен содержать только цифры и буквы [a-z, A-Z, 0-9]");
                hasError = true;
            }
            if (!lastNameTextField.getValue().matches("[А-Яа-я]+")){
                Notification.show("Фамилия должна содержать только  буквы [А-Я, а-я]");
                hasError = true;
            }
            if (!firstNameTextField.getValue().matches("[А-Яа-я]+")){
                Notification.show("Имя должно содержать только  буквы [А-Я, а-я]");
                hasError = true;
            }
            if (!patronymicTextField.getValue().matches("[А-Яа-я]+")){
                Notification.show("Отчество должно содержать только  буквы [А-Я, а-я]");
                hasError = true;
            }
            if (!emailTextField.getValue().matches("[A-Za-z0-9@.]{5,256}+")){
                Notification.show("Почта не соотвествует ожидаемому формату");
                hasError = true;
            }
            if (!addressTextField.getValue().matches("[а-яА-Я0-9]{1,1024}+")) {
                Notification.show("Адрес не соотвествует ожидаемому формату");
                hasError = true;
            }

            if (hasError){
                return;
            }else {
                var person = new Person()
                        .setRole("CUSTOMER")
                        .setLogin(loginTextField.getValue())
                        .setPassword(passwordTextField.getValue())
                        .setFirstName(firstNameTextField.getValue())
                        .setLastName(lastNameTextField.getValue())
                        .setPatronymic(patronymicTextField.getValue())
                        .setPhone(phoneTextField.getValue())
                        .setEmail(emailTextField.getValue())
                        .setAddress(addressTextField.getValue());
                personService.save(person);
                Notification.show("Регистрация выполнена успешно.");
                UI.getCurrent().navigate("login");
            }
        });

        add(loginTextField, passwordTextField, firstNameTextField, lastNameTextField, patronymicTextField,
            emailTextField, addressTextField, phoneTextField, registrationButton);
    }

    private TextField initTextFieldWithPlaceholder(String placeString){
        var textField = new TextField();
        textField.setPlaceholder(placeString);
        return  textField;
    }
}
