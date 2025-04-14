package fr.enchere.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    @GetMapping("/test")
    public String home() {
        return "test";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
