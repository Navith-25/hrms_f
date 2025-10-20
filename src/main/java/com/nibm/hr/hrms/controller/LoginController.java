package com.nibm.hr.hrms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        // This will return "login.html" from the templates folder
        return "login";
    }
}
