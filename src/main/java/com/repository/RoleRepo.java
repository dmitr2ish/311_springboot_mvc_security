package com.repository;

import com.entity.Role;
import com.entity.User;

import java.util.List;

public interface RoleRepo {
    Role getByName(String name);

    List<Role> getAll();

    void add(Role role);
}
