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
        if (user.getId() == null) {
            manager.persist(user);
        } else {
            manager.merge(user);
        }
        System.out.println("user saved with id: " + user.getId());
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
    public void delete(User user) {
        User mergedUser = manager.merge(user);
        manager.remove(mergedUser);
        System.out.println("User with id: " + mergedUser.getId() + " deleted successfully");
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
