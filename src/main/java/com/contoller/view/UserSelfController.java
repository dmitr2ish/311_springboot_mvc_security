package com.contoller.view;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/aboutself")
public class UserSelfController {

    @GetMapping
    public ModelAndView userPage(Authentication authentication) {
        ModelAndView mav = new ModelAndView("userself/user");
        mav.addObject("user", authentication.getPrincipal());
        return mav;
    }
}
