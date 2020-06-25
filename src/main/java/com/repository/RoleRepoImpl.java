package com.repository;

import com.entity.Role;
import com.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class RoleRepoImpl implements RoleRepo{

    @Autowired
    private EntityManager manager;

    @Override
    public Role getByName(String name) {
        Role role = (Role) manager.createQuery("select c from User c where c.name = :name")
                .setParameter("name", name)
                .getSingleResult();
        return role;
    }

    @Override
    public List<Role> getAll() {
        return manager.createQuery("select c from User c").getResultList();
    }

    @Override
    public void add(Role role) {
        manager.persist(role);
    }
}
