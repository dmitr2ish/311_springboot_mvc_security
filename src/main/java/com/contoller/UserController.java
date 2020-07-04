package com.contoller;

import com.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @GetMapping
    public String userPage(Authentication authentication, ModelMap modelMap) {
        User user = (User) authentication.getPrincipal();
        modelMap.addAttribute("user", user);
        return "user/user";
    }
}
