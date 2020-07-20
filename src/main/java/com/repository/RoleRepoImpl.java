package com.repository;

import com.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class RoleRepoImpl implements RoleRepo {

    private EntityManager manager;
    final private String tableRoles;
    final private String tableUsers;
    final private String tableUserWithRoles;

    @Autowired
    public RoleRepoImpl(EntityManager manager) {
        this.manager = manager;
        tableRoles = "311_roles";
        tableUsers = "311_user";
        tableUserWithRoles = "311_users_roles";
    }

    @Override
    public Role getByName(String name) {
        return manager.createQuery("select c from Role c where c.name = :name", Role.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Role> getAll() {
        return manager.createQuery("select c from Role c").getResultList();
    }

    @Override
    public void add(Role role) {
        manager.persist(role);
    }
}
