package com.contoller;

import com.entity.Role;
import com.entity.User;
import com.service.UserRepr;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/admin/*")
public class AdminController {

    private UserService service;

    @Autowired
    public AdminController(UserService service) {
        this.service = service;
    }

    @RequestMapping
    public String adminPage() {
        return "admin/admin";
    }

    @GetMapping
    public String listPage(Authentication authentication, ModelMap modelMap) {
        List<User> list = service.getAllUsers();
        User user = (User) authentication.getPrincipal();
        modelMap.addAttribute("list", list);
        modelMap.addAttribute("user", user);
        modelMap.addAttribute("newUser", new UserRepr());
        return "admin/list";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateUser(@RequestParam(required = false, value = "id") Long id,
                             @RequestParam(required = false, value = "firstName") String firstName,
                             @RequestParam(required = false, value = "lastName") String lastName,
                             @RequestParam(required = false, value = "age") Byte age,
                             @RequestParam(required = false, value = "email") String email,
                             @RequestParam(required = false, value = "password") String password,
                             @RequestParam(required = false, value = "roles") String flag) {

        Role adminRole = service.getRoleByName("ADMIN");
        Role userRole = service.getRoleByName("USER");

        List<Role> roleList = new ArrayList<>();

        switch (flag) {
            case "ADMIN":
                roleList.add(adminRole);
                break;
            case "ADMIN,USER":
                roleList.add(adminRole);
                roleList.add(userRole);
                break;
            default:
                roleList.add(userRole);
                break;
        }

        User currentUser = service.getById(id);
        currentUser.setFirstName(firstName);
        currentUser.setLastName(lastName);
        currentUser.setAge(age);
        currentUser.setEmail(email);
        currentUser.setPassword(password);
        currentUser.setRoles(roleList);
        service.update(currentUser);
        return "redirect:/admin/";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteUser(@ModelAttribute(name = "user") User user) {
        service.delete(user);
        return "redirect:/admin/";
    }

    @RequestMapping(value = "deleteall")
    public String deleteAllUsers() {
        service.deleteAll();
        return "redirect:/admin/";
    }
}
