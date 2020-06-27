package com.service;

import com.entity.Role;
import com.entity.User;

import java.util.List;

public interface UserService {
    //TODO сделать отдельный интерфейс для UserRepr
    void createUser(UserRepr userRepr);
    void addUser(User user);
    User getById(Long id);
    List<User> getAllUsers();
    void update(User user);
    void delete(User user);
    void deleteAll();
    Role getRoleByName(String name);
    List<Role> getAllRoles();
    void addRole(Role role);
}
