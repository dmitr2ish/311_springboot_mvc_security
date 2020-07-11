package com.contoller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping(value = "/login")
    public ModelAndView loginpage() {
        return new ModelAndView("main/login");
    }
}
