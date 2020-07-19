package com.config.security;


import com.config.handler.LoginSuccessHandler;
import com.service.UserService;
import com.service.security.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserAuthService userService;

    @Autowired
    public SecurityConfig(UserAuthService userService) {
        this.userService = userService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
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
                .antMatchers("/aboutself/*").access("hasAuthority('USER')")
                .anyRequest().authenticated();
    }
}
