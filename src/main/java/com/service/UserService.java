package com.service;

import com.entity.Role;
import com.entity.User;

import java.util.List;

public interface UserService {

    void addUser(User user);

    User getById(Long id);

    List<User> getAllUsers();

    void update(User user);

    void delete(User user);

    Role getRoleByName(String name);

    List<Role> getAllRoles();

    void addRole(Role role);
}
