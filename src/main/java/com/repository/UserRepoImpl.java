package com.repository;

import com.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class UserRepoImpl implements UserRepo{
    @Autowired
    private EntityManager manager;


    @Override
    public void addUser(User user) {
        manager.persist(user);
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }
}
