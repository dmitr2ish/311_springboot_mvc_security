package com.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class MainController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String indexPage() {
        return "main/index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "main/login";
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String errorPage() {
        return "main/error";
    }
}
