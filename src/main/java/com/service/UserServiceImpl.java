package com.service;

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
    public List<User> getAllUsers() {
        return repo.getAllUsers();
    }

    @Override
    public User getUser(Long id) {
        return repo.getById(id);
    }
}
