package ru.geekbrains.myspringshop.frontend;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import ru.geekbrains.myspringshop.config.security.CustomUserDetails;
import ru.geekbrains.myspringshop.config.security.CustomUserDetailsService;
import ru.geekbrains.myspringshop.integration.KeycloakIntegration;
import ru.geekbrains.myspringshop.util.DecodeJwtToken;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route("login")
@PageTitle("Login Form | Vaadin Shop")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private final LoginForm loginForm = new LoginForm();

    private final KeycloakIntegration keycloakIntegration;
    private final CustomUserDetailsService customUserDetailsService;

    public LoginView(KeycloakIntegration keycloakIntegration,
                     CustomUserDetailsService customUserDetailsService){
        this.customUserDetailsService = customUserDetailsService;
        this.keycloakIntegration = keycloakIntegration;

        initLoginView();
    }

    private void initLoginView() {
        setSizeFull();
//        setAlignItems(Alignment.CENTER);
//        setJustifyContentMode(JustifyContentMode.CENTER);

       // loginForm.setAction("login");
        loginByJwtListener();
        var registrationButton = new Button("Регистрация", event ->
            UI.getCurrent().navigate(RegistrationView.class);
        );
        add(new H1("Vaadin Shop"), registrationButton, loginForm);
    }

    private void loginByJwtListener() {
        loginForm.addLoginListener(e -> {
            var bearerToken = keycloakIntegration.authorize(e.getUsername(), e.getPassword());
            var httpServletResponse = (HttpServletResponse) VaadinResponse.getCurrent();
            httpServletResponse.addCookie(new Cookie("ecm-jwt", bearerToken.getAccessToken()));

            var securityContext = SecurityContextHolder.getContext();
            var tokenAuthentication = new BearerTokenAuthenticationToken(bearerToken.getAccessToken());
            tokenAuthentication.setAuthenticated(true);

            securityContext.setAuthentication(tokenAuthentication);

            var customDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(
                    (String) DecodeJwtToken.decodeByKey("sub")
            );

            if (customDetails.getPerson().isDisabled()){
                Map<String, List<String>> queryParams = new HashMap<>();
                queryParams.put("error", List.of(""));

                var queryParameters = new QueryParameters(queryParams);
                UI.getCurrent().navigate("login", queryParameters);
                SecurityContextHolder.clearContext();
                return;
            }
            tokenAuthentication.setDetails(customDetails);

            UI.getCurrent().navigate(MainView.class);
        });
    }
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (event.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            loginForm.setError(true);
        }
    }
}
