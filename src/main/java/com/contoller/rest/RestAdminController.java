package com.contoller.rest;

import ch.qos.logback.core.boolex.EvaluationException;
import com.entity.Role;
import com.entity.User;
import com.entity.Userable;
import com.service.UserRepr;
import com.service.UserReprService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/admin/")
public class RestAdminController {

    final private UserService userService;
    final private UserReprService reprService;

    @Autowired
    public RestAdminController(UserService userService, UserReprService reprService) {
        this.userService = userService;
        this.reprService = reprService;
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<User>> getAllUsers() {
        final List<User> users = userService.getAllUsers();
        return users != null && !users.isEmpty()
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/user")
    public ResponseEntity<?> create(@RequestBody UserRepr userRepr) {
        reprService.createUser(userRepr);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(name = "id") long id) {
        final User user = userService.getById(id);

        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/user")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        List<Role> roleList = new ArrayList<>();

        Role ADMIN_ROLE = userService.getRoleByName("ADMIN");
        Role USER_ROLE = userService.getRoleByName("USER");

        for (Role role : user.getRoles()) {
            if ((role.getName().equals("ADMIN")) && (role.getName().equals("USER"))) {
                roleList.add(ADMIN_ROLE);
                roleList.add(USER_ROLE);
            } else if (role.getName().equals("ADMIN")) {
                roleList.add(ADMIN_ROLE);
            } else {
                roleList.add(USER_ROLE);
            }
        }
        User currentUser = userService.getById(user.getId());
        currentUser.setRoles(roleList);
        currentUser.setFirstName(user.getFirstName());
        currentUser.setLastName(user.getLastName());
        currentUser.setAge(user.getAge());
        currentUser.setEmail(user.getEmail());

        userService.update(currentUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody User requestUser) {
        User user = userService.getById(requestUser.getId());
        userService.delete(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //The method sets the correct roles with an id, since the role comes to the controller without an id

}
