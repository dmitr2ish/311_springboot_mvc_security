package com.contoller.view;

import com.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping
    public ModelAndView userPage(Authentication authentication) {
        ModelAndView mav = new ModelAndView("user/user");
        mav.addObject("user", authentication.getPrincipal());
        return mav;
    }
}