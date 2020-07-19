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
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin/")
public class AdminController {

    final private UserService service;

    @Autowired
    public AdminController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ModelAndView listPage(Authentication authentication) {
        return new ModelAndView("admin/list")
                .addObject("user", new User())
                .addObject("currentUser", authentication.getPrincipal());
    }

    @ModelAttribute("roles")
    public List<Role> roles() {
        return service.getAllRoles();
    }
}
