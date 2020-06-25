package com.contoller;

import com.entity.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value = "/admin/*")
public class AdminController {

    @Autowired
    private UserService service;

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

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerPage(Model model) {
        User user = new User();
        model.addAttribute("newUser", user);
        return "admin/registration";

    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute(name = "newUser") User user) {
        service.addUser(user);
        System.out.println(user.toString());
        return "redirect:/admin/list";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editUser(@RequestParam(name = "id") Long id, ModelMap map) {
        User user = service.getById(id);
        map.addAttribute("user", user);
        return "admin/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateUser(@ModelAttribute(name = "user") User user) {
        service.update(user);
        return "redirect:/admin/list";
    }

}
