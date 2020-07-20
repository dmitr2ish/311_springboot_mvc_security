package com.repository;

import com.entity.User;

import java.util.List;

public interface UserRepo {

    void addUser(User user);

    void update(User user);

    User getById(Long id);

    User getByEmail(String email);

    List<User> getAllUsers();

    boolean isExist(User user);

    void delete(User user);

    boolean deleteById(Long id);
}
