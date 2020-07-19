package com.service;

import com.entity.Role;
import com.entity.User;
import com.repository.RoleRepo;
import com.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    final private UserRepo userRepo;

    final private RoleRepo roleRepo;

    final private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo, BCryptPasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void addUser(User user) {
        String pass = user.getPassword();
        user.setPassword(passwordEncoder.encode(pass));
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
        userRepo.update(user);
    }

    @Override
    public void delete(User user) {
        userRepo.delete(user);
    }

    @Override
    public boolean deleteById(Long id) {
        return userRepo.deleteById(id);
    }

    @Override
    public void deleteAllUsers() {
        userRepo.deleteAll();
    }

    @Override
    public void deleteAllRoles() {
        roleRepo.deleteAll();
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
