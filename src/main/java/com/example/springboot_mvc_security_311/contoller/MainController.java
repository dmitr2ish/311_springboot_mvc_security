package com.example.springboot_mvc_security_311.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping
    public String pageHello(Model model){
        return "hello";
    }

}
