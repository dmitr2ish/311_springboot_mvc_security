package com.repository;

import com.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepo {
    void addUser(User user);
    List<User> getAllUsers();
}
