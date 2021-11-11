package ru.geekbrains.myspringshop.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(@Lazy CustomUserDetailsService customUserDetailsService) {
        this.userDetailsService = customUserDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll() // для активации vaadin
                .anyRequest().authenticated() //каждый запрос д.б авторизован
                .and().formLogin()// добавление формы аутентификации
                .loginPage("/login").permitAll()// для этой страницы доступ для всех
                .loginProcessingUrl("/login")// путь (end point), по которму будет проходить процесс аутотенфикации
                .failureUrl("/login?error") // при ошибке аутентификации выдавать форму ошибки
                .and().logout().logoutSuccessUrl("/login"); // при log out остаемся на странице с адрессом /login
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                // для игнорирвания элементов vaadin при проходе по данным ссылкам, иначе будет выдаваться ошибка
                "/VAADIN/**", // ссылки - иконки vaadin
                "/favicon.ico",
                "/robots.txt",
                "/manifest.webmanifest",
                "/sw.js",
                "/offline.html",
                "/icons/**",
                "/images/**",
                "/styles/**",
                "/h2-console/**"
        );
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        // здесь создается объект, который будет шифровать переданный пароль для хранения  в БД
        return new BCryptPasswordEncoder();
    }

    @Bean
    // объект нужен для прохождения авторизации с зашифрованным паролем
    // ( в нашем случае это процесс, который запускается springSecurity по 'end point'у - "/login")
    public DaoAuthenticationProvider authenticationProvider() {
        var auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService); // userDetailsService - объект, который помогает springSecurity определить, что пользователь залогинился
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }
}
