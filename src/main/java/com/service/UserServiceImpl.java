package com.service;

import com.entity.Role;
import com.entity.User;
import com.repository.RoleRepo;
import com.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public void addUser(User user) {
        userRepo.addUser(user);
    }

    @Override
    public User getById(Long id) {
        return userRepo.getById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.getAllUsers();
    }

    @Override
    public void update(User user) {
        userRepo.addUser(user);
    }

    @Override
    public void delete(User user) {
        userRepo.delete(user);
    }

    @Override
    public void deleteAll() {
        userRepo.deleteAll();
    }

    @Override
    public Role getRoleByName(String name) {
        return roleRepo.getByName(name);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepo.getAll();
    }

    @Override
    public void addRole(Role role) {
        roleRepo.add(role);
    }
}
