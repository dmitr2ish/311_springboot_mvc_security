package com.contoller;

import com.entity.Role;
import com.entity.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listPage(ModelMap modelMap) {
        List<User> list = service.getAllUsers();
        modelMap.addAttribute("list", list);
        return "admin/list";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editUser(@RequestParam(name = "id") Long id, ModelMap map) {
        User user = service.getById(id);
        map.addAttribute("user", user);
        return "admin/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateUser(@ModelAttribute(name = "user") User user,
                             @RequestParam("option") String flag) {
        List<Role> roleList = (user.getRoles() == null) ? new ArrayList<>() : user.getRoles();
        Role adminRole = service.getRoleByName("ADMIN");
        Role userRole = service.getRoleByName("USER");

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

        user.setRoles(roleList);
        service.update(user);
        return "redirect:/admin/list";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteUser(@ModelAttribute(name = "user") User user) {
        service.delete(user);
        return "redirect:/admin/list";
    }

    @RequestMapping(value = "deleteall")
    public String deleteAllUsers() {
        service.deleteAll();
        return "redirect:/admin/list";
    }
}
