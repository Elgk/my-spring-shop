package ru.geekbrains.myspringshop.frontend;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import ru.geekbrains.myspringshop.entity.Person;
import ru.geekbrains.myspringshop.entity.Product;
import ru.geekbrains.myspringshop.service.PersonService;


@Route("person-list")
public class PersonListView extends AbstractView{
    private final PersonService personService;

    private final Grid<Person> personGrid = new Grid<>(Person.class);

    public PersonListView(PersonService personService){
        this.personService = personService;
        
        initPersonGrid();
        add(personGrid);
    }

    private void initPersonGrid() {
        var persons = personService.findAll();
        personGrid.setItems(persons);
        personGrid.setColumns("login","firstName","lastName","isDisabled");
        personGrid.setSizeUndefined();
        personGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        ListDataProvider<Person> dataProvider = DataProvider.ofCollection(persons);
        personGrid.setDataProvider(dataProvider);

        personGrid.addColumn(new ComponentRenderer<>(item -> {
            var disabled = new Checkbox(item.isDisabled());
            disabled.addValueChangeListener((HasValue.ValueChangeListener< AbstractField.ComponentValueChangeEvent<Checkbox, Boolean>>)
                    checkboxBooleanComponentChangeEvent -> {
                        item.setDisabled(disabled.getValue());
                        personService.update(item);
                    });
            return new HorizontalLayout(disabled);
        }));

    }

}
