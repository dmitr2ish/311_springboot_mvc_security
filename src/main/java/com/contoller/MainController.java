package com.contoller;

import com.entity.Role;
import com.entity.User;
import com.service.UserRepr;
import com.service.UserReprService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@Controller
public class MainController {

    private UserService service;

    private UserReprService reprService;

    @Autowired
    public MainController(UserService service, UserReprService reprService) {
        this.service = service;
        this.reprService = reprService;
    }

    @GetMapping(value = "/newuser")
    public ModelAndView toUserForm() {
        return new ModelAndView("main/form")
                .addObject("user", new UserRepr());
    }

    @RequestMapping(value = "/secretadminpage", method = RequestMethod.GET)
    public String secretPage() {
        service.addRole(new Role("ADMIN"));
        service.addRole(new Role("USER"));
        UserRepr user = new UserRepr();
        user.setFirstName("test");
        user.setLastName("test");
        user.setEmail("test@test.ru");
        user.setPassword("test");
        user.setRepeatPassword("test");
        user.setAge((byte)18);
        user.setRoleList(service.getAllRoles());
        reprService.createUser(user);
        return "redirect:/login";
    }

    //TODO    Страница для тестов убрать как станет не нужна
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String indexPage() {
        return "main/index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "main/login";
    }

//TODO убрать если не пригодится
//    @RequestMapping(value = "/reg", method = RequestMethod.GET)
//    public String registerPage(Model model, Authentication authentication) {
//        User user = (User) authentication.getPrincipal();
//        model.addAttribute("principal", user);
//        model.addAttribute("user", new UserRepr());
//        return "main/reg";
//    }

    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    public String saveUser(@Valid UserRepr userRepr,
                           BindingResult bindingResult,
                            @RequestParam(required=false, value = "flag") String flag) {
        if (bindingResult.hasErrors()) {
            //если есть ошибки на странице остаемся на той же странице
            return "redirect:reg";
        }
        if (!userRepr.getPassword().equals(userRepr.getRepeatPassword())) {
            //если пароли не совпадают генерим ошибку и остаемся на странице
            bindingResult.rejectValue("password", "", "Пароли не совпадают");
            return "redirect:reg";
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

        reprService.createUser(userRepr);
        return "redirect:/admin/list";
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String errorPage() {
        return "main/error";
    }
}
