package ru.geekbrains.myspringshop.config.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;

public class SecurityUtils {
    // данный метод утверждает, что все запросы происходят внутри фреймворка
    public static boolean isFrameworkInternalRequest(HttpServletRequest request) {
        return true;
    }

    // SecurityContextHolder - хранит текущую аутентификацию пользователя
    public static boolean isUserLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication != null
                && !(authentication instanceof AnonymousAuthenticationToken)
             //   && ((CustomUserDetails) authentication.getDetails()).isCredentialsNonExpired()
                && authentication.isAuthenticated();
    }
}
