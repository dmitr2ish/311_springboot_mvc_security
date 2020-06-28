package com.repository;

import com.entity.User;

import java.util.List;

public interface UserRepo {
    void addUser(User user);

    User getById(Long id);

    User getByName(String name);

    List<User> getAllUsers();

    boolean isExist(User user);

    void delete(User user);

    void deleteAll();
}
