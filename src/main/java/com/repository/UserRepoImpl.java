package com.repository;

import com.entity.Role;
import com.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class UserRepoImpl implements UserRepo {

    final private EntityManager manager;
    final private String tableRoles;
    final private String tableUsers;
    final private String tableUserWithRoles;

    @Autowired
    public UserRepoImpl(EntityManager manager) {
        this.manager = manager;
        tableRoles = "311_roles";
        tableUsers = "311_user";
        tableUserWithRoles = "311_users_roles";
    }

    @Override
    public void addUser(User user) {
//        String deleteAllUserQuery = "insert into " + tableUsers + " values (:id,:age,:email,:first_name,:last_name,:password)";
//        manager.createNativeQuery(deleteAllUserQuery)
//                .setParameter("id", user.getId())
//                .setParameter("age", user.getAge())
//                .setParameter("email", user.getEmail())
//                .setParameter("first_name", user.getFirstName())
//                .setParameter("last_name", user.getLastName())
//                .setParameter("password", user.getPassword())
//                .executeUpdate();
        manager.persist(user);
    }

    @Override
    public void update(User user) {
        manager.merge(user);
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
        manager.remove(user);
    }

    @Override
    public void deleteAll() {
        String deleteAllUserQuery = "delete FROM " + tableUsers + " where id >= 0;";
        manager.createNativeQuery(deleteAllUserQuery)
                .executeUpdate();
    }

    @Override
    public boolean deleteById(Long id){
        String deleteLink = "delete FROM " + tableUserWithRoles + " where user_id = :id;";
        String deleteByIdQuery = "delete FROM " + tableUsers + " where id = :id;";
        manager.createNativeQuery(deleteLink)
                .setParameter("id", id)
                .executeUpdate();

        int countDeleteUser = manager.createNativeQuery(deleteByIdQuery)
                .setParameter("id", id)
                .executeUpdate();
        return countDeleteUser != 0;
    }

    @Override
    public User getById(Long id) {
        return manager.find(User.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public User getByEmail(String email) {
        List<User> user = manager.createQuery("select c from User c where c.email = :email")
                .setParameter("email", email)
                .getResultList();
        return user.get(0);
    }
}
