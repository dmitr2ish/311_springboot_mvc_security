package com.contoller.view;

import com.entity.Role;
import com.entity.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class MainController {

    final private UserService service;

    @Autowired
    public MainController(UserService service) {
        this.service = service;
    }

    @GetMapping(value = "/login")
    public ModelAndView loginpage() {
        return new ModelAndView("main/login");
    }

    @RequestMapping(value = "/secretadminpage", method = RequestMethod.GET)
    public String secretPage() {
        service.addRole(new Role("ADMIN"));
        service.addRole(new Role("USER"));
        User user = new User();
        user.setFirstName("test");
        user.setLastName("test");
        user.setEmail("test@test.ru");
        user.setPassword("test");
        user.setAge((byte) 18);
        user.setRoles(service.getAllRoles());
        service.addUser(user);
        return "redirect:/login";
    }
}
