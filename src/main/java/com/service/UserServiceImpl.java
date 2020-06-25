package com.service;

import com.entity.Role;
import com.entity.User;
import com.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo repo;

    @Override
    public void addUser(User user) {
        repo.addUser(user);
    }

    @Override
    public User getById(Long id) {
        return repo.getById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return repo.getAllUsers();
    }

    @Override
    public void update(User user) {
        repo.update(user);
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    public void deleteAll() {
        repo.deleteAll();
    }

    @Override
    public Role getRoleByName(String name) {
        return null;
    }

    @Override
    public List<Role> getAllRoles() {
        return null;
    }

    @Override
    public void addRole(Role role) {

    }
}
