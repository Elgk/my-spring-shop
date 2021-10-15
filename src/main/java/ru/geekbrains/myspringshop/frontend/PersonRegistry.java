package ru.geekbrains.myspringshop.frontend;


import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import ru.geekbrains.myspringshop.entity.Person;
import ru.geekbrains.myspringshop.entity.repository.PersonRepository;
import ru.geekbrains.myspringshop.service.PersonService;


@Route("registry")
public class PersonRegistry extends VerticalLayout {
    private final PersonService personService;
    private String lastNameValue;
    private String firstNameValue;
    private String phoneValue;
    private String passwordValue;

    
    public PersonRegistry(PersonService personService){
        this.personService = personService;
        initPage();
    }

    private void initPage() {
        HorizontalLayout titleLayout = new HorizontalLayout();
        HorizontalLayout subTitleLayout = new HorizontalLayout();
        Text titleText = new Text("Форма регистрации");
        Text subTitleText = new Text("Введите ваши данные: ");
        titleLayout.add(titleText);
        subTitleLayout.add(subTitleText);

        var dataInputLayout = initInputLayout();
        var saveLayout = initSaveButton();

        add(titleLayout, subTitleLayout, dataInputLayout, saveLayout);
    }

    private HorizontalLayout initInputLayout(){
        final TextField firstName = new TextField("First name");
        final TextField lastName = new TextField("Last name");
        final TextField phone = new TextField("Phone");
        final PasswordField password = new PasswordField("Password");

        fieldSetting(firstName, false);
        fieldSetting(lastName, true);
        fieldSetting(phone, true);
        passwordFieldtSetting(password);


        firstName.addValueChangeListener(event ->{
            firstNameValue = event.getValue();
        });
        lastName.addValueChangeListener(event ->{
            lastNameValue = event.getValue();
        });
        phone.addValueChangeListener(event ->{
            phoneValue = event.getValue();
        });
        password.addValueChangeListener(event ->{
            passwordValue = event.getValue();
        });

        return new HorizontalLayout(firstName, lastName, phone, password);
    }

    private HorizontalLayout initSaveButton(){
        var saveButton = new Button("Сохранить", event -> {
            Person person = new Person();
            person.setFirstName(firstNameValue);
            person.setLastName(lastNameValue);
            person.setPhone(phoneValue);
            person.setPassword(passwordValue);
            personService.save(person);
            Notification.show("Регистрация выполнена успешно.");
        });

        return new HorizontalLayout(saveButton);
    }

    private void fieldSetting(TextField field, boolean required){
        field.setMinLength(100);
        field.setAutoselect(true);
        field.setClearButtonVisible(true);
        field.setRequired(required);
    }
    private void passwordFieldtSetting(PasswordField field){
        field.setMinLength(100);
        field.setAutoselect(true);
        field.setClearButtonVisible(true);
        field.setRequired(true);
    }


}
