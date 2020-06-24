package com.repository;

import com.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepo {
    void addUser(User user);
    User getById(Long id);
    User getByName(String name);
    List<User> getAllUsers();
    boolean isExist(User user);
    void update(User user);
    void deleteById(Long id);
    void deleteAll();
}
