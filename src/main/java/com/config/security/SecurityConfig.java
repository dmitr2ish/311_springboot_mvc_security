package com.config.security;

import com.config.handler.LoginSuccessHandler;
import com.service.security.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private PasswordEncoder passwordEncoder;

    private UserAuthService userAuthService;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserAuthService(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                //указываем страницу с формой логина
                .loginPage("/login")
                //указываем логику обработки при логине
                .successHandler(new LoginSuccessHandler())
                //указываем action с формы логина
                .loginProcessingUrl("/authenticateTheUser")
                //указываем параметры логина и пароля с формы
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                //даем доступ к форме логина всем
                .permitAll();

        http.logout()
                //разрешаем делать логаут всем
                .permitAll()
                //указываем url логаута
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                //указываем url при удачном логауте
                .logoutSuccessUrl("/login?logout")
                .and()
                .csrf().disable();

        http
                //делаем страницу регистрации недоступной для зарегистрированных пользователей
                .authorizeRequests()
                //страница аутентификации доступна всем
                .antMatchers("/login").anonymous()
                .antMatchers("/secretadminpage").anonymous()//TODO оставил для тестов добавляет пользователя с правами admin и user
                //.antMatchers("/reg").anonymous()
                //защищенные url
                .and()
                .authorizeRequests()
                .antMatchers("/admin/*").access("hasAuthority('ADMIN')")
                .and()
                .authorizeRequests()
                .antMatchers("/user/*").access("hasAuthority('USER')")
                .anyRequest().authenticated();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userAuthService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }
}
