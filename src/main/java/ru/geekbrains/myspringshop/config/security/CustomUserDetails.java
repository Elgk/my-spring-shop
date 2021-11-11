package ru.geekbrains.myspringshop.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.geekbrains.myspringshop.entity.Person;
import ru.geekbrains.myspringshop.util.DecodeJwtToken;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final Person person;
    private final List<GrantedAuthority> authorities;

    public CustomUserDetails(Person person){
        this.person = person;
      //  this.authorities = List.of(new SimpleGrantedAuthority(person.getRole()));
        this.authorities = DecodeJwtToken.getRoles();
    }
    // определяется как находить пароль и др детали пользователя (на данный момент пользователь уже найден, т.е. идентифицировался)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return person.getPassword();
    }

    @Override
    public String getUsername() {
        return person.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        int exp = (int) DecodeJwtToken.decodeByKey("exp");
        long now = Instant.now().toEpochMilli() / 1000;

        return exp > now;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Person getPerson() {
        return person;
    }
}
