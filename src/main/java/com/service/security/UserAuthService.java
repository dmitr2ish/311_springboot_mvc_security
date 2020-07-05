package com.service.security;

import com.entity.User;
import com.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService implements UserDetailsService {

    private final UserRepo repo;

    @Autowired
    public UserAuthService(UserRepo repo) {
        this.repo = repo;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.getByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь с именем " + username + "не найден");
        }
        return user;
    }
}
