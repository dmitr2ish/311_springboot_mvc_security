package com.repository;

import com.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class UserRepoImpl implements UserRepo {

    @Autowired
    private EntityManager manager;

    @Override
    public void addUser(User user) {
        manager.persist(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        return manager.createQuery("select c from User c").getResultList();
    }

    @Override
    public boolean isExist(User user) {
        return manager.contains(user);
    }

    @Override
    public void update(User user) {
        manager.createQuery("update User set name = :name, password = :password where id = :id")
        .setParameter("id",user.getId())
        .setParameter("name", user.getName())
        .setParameter("password", user.getPassword())
        .executeUpdate();
    }

    @Override
    public void deleteById(Long id) {
        User user = getById(id);
        manager.remove(user);
    }

    @Override
    public void deleteAll() {
        manager.clear();
    }

    @Override
    public User getById(Long id) {
        return manager.find(User.class, id);
    }

    @Override
    public User getByName(String name) {
        return manager.find(User.class, name);
    }
}
