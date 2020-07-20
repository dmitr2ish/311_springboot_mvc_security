package com.contoller.view;

import com.entity.Role;
import com.entity.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MainController {

    final private UserService service;

    @Autowired
    public MainController(UserService service) {
        this.service = service;
    }

    @GetMapping(value = "/login")
    public ModelAndView loginpage() {
        return new ModelAndView("login");
    }

    @GetMapping(value = "/")
    public ModelAndView listPage(Authentication authentication) {
        return new ModelAndView("list")
                .addObject("user", new User())
                .addObject("currentUser", authentication.getPrincipal());
    }

    @ModelAttribute("roles")
    public List<Role> roles() {
        return service.getAllRoles();
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
