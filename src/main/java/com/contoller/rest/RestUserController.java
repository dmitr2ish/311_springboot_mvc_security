package com.contoller.rest;

import com.entity.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/user")
public class RestUserController {

    @Autowired
    private UserService service;

    @GetMapping(value = "/currentUser")
    public User getInformationCurrentUser(Authentication authentication) {

        return (User) authentication.getPrincipal();
    }
}
