package ru.geekbrains.myspringshop.util;

import org.springframework.security.core.context.SecurityContextHolder;
import ru.geekbrains.myspringshop.config.security.CustomUserDetails;
import ru.geekbrains.myspringshop.entity.Person;

public class PersonUtil {
    public static Person getCurrentPerson() {
        var details = SecurityContextHolder.getContext().getAuthentication().getDetails();

        if (details instanceof CustomUserDetails) {
            return ((CustomUserDetails) details).getPerson();
        }
        return null;
    }
}
