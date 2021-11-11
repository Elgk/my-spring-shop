package ru.geekbrains.myspringshop.config.security;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import org.springframework.stereotype.Component;
import ru.geekbrains.myspringshop.frontend.LoginView;
import ru.geekbrains.myspringshop.frontend.RegistrationView;

@Component
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {
    // предоставлена логика перед тем как пользователь будет проходить идентификацию и аутентификацию
    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.getSource().addUIInitListener(uiEvent ->{
            final UI ui = uiEvent.getUI();
            ui.addBeforeEnterListener(this::beforeEnter); // добавляется событие beforeEnter
        });
    }

    // не пускает на другие формы, если пользователь не прошел аутентификацию
    private void beforeEnter(BeforeEnterEvent event){
        if (!LoginView.class.equals(event.getNavigationTarget())
            && !RegistrationView.class.equals(event.getNavigationTarget())
            && !SecurityUtils.isUserLoggedIn()){
            event.rerouteTo(LoginView.class);
        }
    }
}
