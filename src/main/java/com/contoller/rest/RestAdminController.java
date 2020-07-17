package com.contoller.rest;

import com.entity.User;
import com.service.UserRepr;
import com.service.UserReprService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestAdminController {

    private final UserService service;
    final private UserReprService reprService;

    @Autowired
    public RestAdminController(UserService service, UserReprService reprService) {
        this.service = service;
        this.reprService = reprService;
    }

    @GetMapping(value = "/admin/list")
    public ResponseEntity<List<User>> getAllUsers() {
        final List<User> users = service.getAllUsers();
        return users != null && !users.isEmpty()
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/admin/reg")
    public ResponseEntity<?> create(@RequestBody UserRepr userRepr) {
        reprService.createUser(userRepr);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
