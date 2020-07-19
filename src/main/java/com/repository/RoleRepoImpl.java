package com.repository;

import com.entity.Role;
import com.entity.User;
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

    public void linkRoles(User userWithId, List<Role> roles) {
        String queryLinkRoles = "inser into " + tableUserWithRoles + " (user_id, role_id) VALUES (:user_id, :role_id);";
        String roleName;
        Role currentRole;
        for (Role role : roles) {
            roleName = role.getName();
            currentRole = getByName(roleName);

            manager.createNativeQuery(queryLinkRoles)
                    .setParameter("user_id", userWithId.getId())
                    .setParameter("role_id", currentRole.getId())
                    .executeUpdate();
        }
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

    @Override
    public void deleteAll() {
        String deleteLink = "delete FROM " + tableUserWithRoles + " where user_id >= 0;";
        String deleteRoles = "delete FROM " + tableRoles + " where id >= 0;";

        manager.createNativeQuery(deleteLink)
                .executeUpdate();

        manager.createNativeQuery(deleteRoles)
                .executeUpdate();
    }
}
