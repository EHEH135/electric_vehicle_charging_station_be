package com.example.electricStation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomeController {

    @GetMapping("/login")
    public String loginController() {
        return "oauthLogin";
    }

    @GetMapping("/articles")
    public String articlesController() {
        return "article";
    }
}
