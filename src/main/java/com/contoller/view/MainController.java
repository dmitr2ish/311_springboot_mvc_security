package com.contoller.view;

import com.entity.Role;
import com.service.UserRepr;
import com.service.UserReprService;
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
    final private UserReprService serviceRepr;

    @Autowired
    public MainController(UserService service, UserReprService serviceRepr) {
        this.service = service;
        this.serviceRepr = serviceRepr;
    }

    @GetMapping(value = "/login")
    public ModelAndView loginpage() {
        return new ModelAndView("main/login");
    }

    @RequestMapping(value = "/secretadminpage", method = RequestMethod.GET)
    public String secretPage() {
        //sequence is important
        service.deleteAllRoles();
        service.deleteAllUsers();

        service.addRole(new Role("ADMIN"));
        service.addRole(new Role("USER"));
        UserRepr user = new UserRepr();
        user.setFirstName("test");
        user.setLastName("test");
        user.setEmail("test@test.ru");
        user.setPassword("test");
        user.setRepeatPassword("test");
        user.setAge((byte) 18);
        user.setRoleList(service.getAllRoles());
        serviceRepr.createUser(user);
        return "redirect:/login";
    }
}
