package com.contoller;

import com.entity.Role;
import com.service.UserRepr;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@Controller
public class MainController {

    @Autowired
    private UserService service;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String indexPage() {
        return "main/index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "main/login";
    }

    @RequestMapping(value = "/reg", method = RequestMethod.GET)
    public String registerPage(Model model) {
        model.addAttribute("user", new UserRepr());
        return "main/reg";
    }

    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    public String saveUser(@Valid UserRepr userRepr,
                           BindingResult bindingResult,
                           @RequestParam("option") String flag) {
        if (bindingResult.hasErrors()) {
            //если есть ошибки на странице остаемся на той же странице
            return "main/reg";
        }
        if (!userRepr.getPassword().equals(userRepr.getRepeatPassword())) {
            //если пароли не совпадают генерим ошибку и остаемся на странице
            bindingResult.rejectValue("password", "", "Парольи не совпадают");
            return "main/reg";
        }

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

        userRepr.setRoleList(roleList);

        service.createUser(userRepr);
        return "redirect:login";
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String errorPage() {
        return "main/error";
    }
}
